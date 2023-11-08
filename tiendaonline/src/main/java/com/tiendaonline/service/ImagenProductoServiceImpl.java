package com.tiendaonline.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenProductoServiceImpl implements ImagenProductoService {

    // Genera un nombre de archivo único utilizando un UUID.
    private String generarUUIDComoNombre() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    // Obtiene la extensión de un nombre de archivo.
    private String obtenerExtension(String nombreOriginal) {
        int extensionIndex = nombreOriginal.lastIndexOf('.');
        return (extensionIndex != -1) ? nombreOriginal.substring(extensionIndex) : "";
    }

    // Obtiene el nombre original de un archivo sin la extensión.
    private String obtenerNombreOriginal(String nombreOriginal) {
        int extensionIndex = nombreOriginal.lastIndexOf('.');
        return (extensionIndex != -1) ? nombreOriginal.substring(0, extensionIndex) : nombreOriginal;
    }

    // Ubicación del contenedor definido en application properties.
    @Value("${storage.location.productos}")
    private String storageLocationProductos;

    // Inicia o prepara el contenedor de imágenes necesario para almacenar imágenes de productos.
    @PostConstruct
    @Override
    public void IniciarContenedorImagenes() {
        
        try {
            Files.createDirectories(Paths.get(storageLocationProductos));
        } catch (IOException exception) {
            throw new RuntimeException("No se pudo crear el directorio de almacenamiento de imágenes.", exception);
        }
        
    }

    // Almacena una imagen en el sistema de archivos asociada a un producto identificado por su ID.
    @Override
    public String AlmacenarUnaImagen(Long id_producto, MultipartFile imagen) {

        // Validación: se verifica si se proporciona una imagen.
        if (imagen == null) {
            throw new IllegalArgumentException("No se proporcionó ninguna imagen para cargar.");
        }

        // Validación: se verifica si la imagen está vacía (no contiene datos).
        if (imagen.isEmpty()) {
            throw new IllegalArgumentException("La imagen está vacía.");
        }

        // Validación: se verifica si el tipo MIME del archivo corresponde a una imagen válida.
        String contentType = imagen.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("El tipo de archivo no es una imagen válida.");
        }

        try {

            // Se obtiene la extensión del archivo de imagen original.
            String extension = obtenerExtension(imagen.getOriginalFilename());
            
            // Se genera un nombre único para la imagen utilizando un UUID (identificador único universal).
            String codigoAleatorio = generarUUIDComoNombre();
            
            // El nuevo nombre se compone del UUID, un guion medio y el nombre original del archivo de imagen con su extensión.
            String nuevoNombre = codigoAleatorio + " - " + obtenerNombreOriginal(imagen.getOriginalFilename()) + extension;
            
            // Se obtiene el flujo de entrada de datos desde el archivo de imagen.
            InputStream inputStream = imagen.getInputStream();

            // Se crea la ruta de destino en el directorio de almacenamiento y se copia el contenido del archivo.
            Path targetPath = Paths.get(storageLocationProductos).resolve(nuevoNombre);
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Se retorna el nombre único de la imagen almacenada con éxito.
            return nuevoNombre;

        } catch (IOException exception) {

            // Si ocurre algún error durante el proceso, se lanza una excepción.
            throw new RuntimeException("Error al almacenar la imagen.", exception);

        }
        
    }

    // Carga una imagen del sistema de archivos a partir de su nombre.
    @Override
    public Path CargarImagen(String nombreImagen) {

        // Se construye la ruta al archivo de imagen utilizando el directorio de almacenamiento y el nombre proporcionado.
        Path imagenPath = Paths.get(storageLocationProductos).resolve(nombreImagen);

        // Se verifica si el archivo de imagen existe en la ruta especificada.
        if (!Files.exists(imagenPath)) {
            throw new IllegalArgumentException("La imagen no existe.");
        }

        // Se devuelve la ruta al archivo de imagen.
        return imagenPath;

    }

    // Carga una imagen como recurso a partir de su nombre.
    @Override
    public Resource CargarComoRecurso(String nombreImagen) {

        try {

            // Obtener la ruta de la imagen llamando al método 'CargarImagen'.
            Path imagen = CargarImagen(nombreImagen);

            // Crear un recurso a partir de la ruta de la imagen.
            Resource recurso = new UrlResource(imagen.toUri());

            // Verificar si el recurso existe y es legible.
            if (recurso.exists() && recurso.isReadable()) {
                return recurso;
            } else {
                throw new RuntimeException("No se pudo cargar la imagen como recurso.");
            }

        } catch (MalformedURLException exception) {

            throw new RuntimeException("Error al cargar la imagen como recurso.", exception);

        }

    }

    // Elimina una imagen del sistema de archivos a partir de su nombre.
    @Override
    public void EliminarImagen(String nombreImagen) {

        // Obtener la ruta de la imagen llamando al método 'CargarImagen'.
        Path imagen = CargarImagen(nombreImagen);
        try {
            
            // Intentar eliminar la imagen.
            Files.delete(imagen);
            
        } catch (IOException exception) {
            
            throw new RuntimeException("Error al eliminar la imagen.", exception);
            
        }

    }

}
