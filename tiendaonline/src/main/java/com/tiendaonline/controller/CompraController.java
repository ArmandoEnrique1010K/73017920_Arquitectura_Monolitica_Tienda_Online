package com.tiendaonline.controller;

import com.tiendaonline.dto.CarritoDto;
import com.tiendaonline.dto.DatosUsuarioDto;
import com.tiendaonline.dto.DetallesCarritoDto;
import com.tiendaonline.dto.ProductoDto;
import com.tiendaonline.service.CarritoService;
import com.tiendaonline.service.CategoriaService;
import com.tiendaonline.service.DetalleCarritoService;
import com.tiendaonline.service.ProductoService;
import com.tiendaonline.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompraController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private DetalleCarritoService detalleCarritoService;

    @GetMapping("/cart")
    public String mostrarCarrito(Model model, Authentication authentication) {
        String username = authentication.getName();
        DatosUsuarioDto datosUsuarioDto = usuarioService.ObtenerUsuarioPorEmail(username);
        // SI SE OBTIENE EL CORREO DEL USUARIO, username = COOREO DEL USUARIO
        logger.info("CORREO DEL USUARIO :" + username);

        // Verifica si el usuario ya tiene un carrito asociado
        CarritoDto carritoDto = carritoService.obtenerCarritoPorUsuario(datosUsuarioDto);

        // Si el usuario no tiene un carrito, puedes manejar esto en la vista para mostrar un mensaje
        model.addAttribute("carrito", carritoDto);
        return "user_carrito.html";
    }

    @PostMapping("/cart")
    public String añadirProductoAlCarrito(Model model, Authentication authentication, @RequestParam Long id_producto, @RequestParam int cantidad) {
        String username = authentication.getName();
        DatosUsuarioDto datosUsuarioDto = usuarioService.ObtenerUsuarioPorEmail(username);

        CarritoDto carritoDto = carritoService.obtenerCarritoPorUsuario(datosUsuarioDto);

        ProductoDto productoDto = productoService.ObtenerProductoPorId(id_producto);

        boolean productoExistente = false;

        DetallesCarritoDto nuevoDetalleDto = null; // Declaración fuera del bloque if

        for (DetallesCarritoDto detalleDto : carritoDto.getDetalles_carrito()) {
            if (detalleDto.getId_producto().equals(id_producto)) {
                int nuevaCantidad = detalleDto.getCantidad() + cantidad;
                double precio = productoDto.getPreciooferta() != null ? productoDto.getPreciooferta() : productoDto.getPrecionormal();
                double nuevoTotal = nuevaCantidad * precio;
                detalleDto.setCantidad(nuevaCantidad);
                detalleDto.setTotal(nuevoTotal);
                productoExistente = true;
                break;
            }
        }

        if (!productoExistente) {
            double precio = productoDto.getPreciooferta() != null ? productoDto.getPreciooferta() : productoDto.getPrecionormal();

            nuevoDetalleDto = new DetallesCarritoDto(); // Definición dentro del bloque if
            nuevoDetalleDto.setId_producto(id_producto);
            nuevoDetalleDto.setNombre_producto(productoDto.getNombre());
            nuevoDetalleDto.setPrecio_producto(precio);
            nuevoDetalleDto.setCantidad(cantidad);
            nuevoDetalleDto.setTotal(precio * cantidad);
            // nuevoDetalleDto.setId_carrito(carritoDto.getId_carrito());
            carritoDto.getDetalles_carrito().add(nuevoDetalleDto);
        }

        if (nuevoDetalleDto != null) { // Asegúrate de que nuevoDetalleDto no sea nulo antes de usarlo
            detalleCarritoService.guardarDetalleCarrito(nuevoDetalleDto);
        }

        model.addAttribute("carrito", carritoDto);
        
        return "redirect:/cart"; // Nombre de la vista Thymeleaf - Para no añadir el mismo producto otra vez cuando se actualiza la página
    }

}

/*
    @GetMapping("/cart")
    public String mostrarCarrito(Model model, Authentication authentication, HttpSession session) {
        String username = authentication.getName();
        DatosUsuarioDto datosUsuarioDto = usuarioService.ObtenerUsuarioPorEmail(username);
        CarritoDto carritoDto = carritoService.obtenerCarritoConDetallesPorUsuario(datosUsuarioDto);

        if (carritoDto == null) {
            logger.info("SE ESTA CREANDO UN NUEVO CARRITO");
            // Crea un nuevo carrito DTO si no existe
            carritoDto = new CarritoDto();
            carritoDto.setFecha_creacion(new Date());

            // Asigna el ID del usuario al carrito
            carritoDto.setDatosUsuarioDto(datosUsuarioDto);
            logger.info("USUARIO: " + datosUsuarioDto);

            carritoDto.setEstado_carrito(true);

            // Inicializa la lista de detalles del carrito
            carritoDto.setDetallesCarritoDto(new ArrayList<>());

            carritoDto = carritoService.guardarCarrito(carritoDto);
        }

        // Recupera los detalles del carrito del carritoDto (lista existente)
        List<DetallesCarritoDto> detallesCarrito = carritoDto.getDetallesCarritoDto();

        model.addAttribute("carrito", carritoDto);
        logger.info("CARRITO: " + carritoDto);
        model.addAttribute("sesion", session.getAttribute("identificadordelusuario"));
        model.addAttribute("detallesCarrito", detallesCarrito);
        logger.info("DETALLES DEL CARRITO: " + detallesCarrito);

        return "user_carrito.html";
    }

    @PostMapping("/cart")
    public String añadirProductoAlCarrito(@RequestParam Long id_producto, @RequestParam int cantidad,
            HttpSession session, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {

        String username = authentication.getName();
        logger.info("USUARIO: " + username);
        DatosUsuarioDto datosUsuarioDto = usuarioService.ObtenerUsuarioPorEmail(username);
        CarritoDto carritoDto = carritoService.obtenerCarritoConDetallesPorUsuario(datosUsuarioDto);

        if (carritoDto == null) {
            carritoDto = new CarritoDto();
            carritoDto.setFecha_creacion(new Date());
            carritoDto.setDatosUsuarioDto(datosUsuarioDto);
            carritoDto.setEstado_carrito(true);
            carritoDto.setDetallesCarritoDto(new ArrayList<>());

            carritoDto = carritoService.guardarCarrito(carritoDto);
        }

        // Verifica si el producto ya existe en el carrito
        boolean productoExistente = false;

        List<DetallesCarritoDto> detallesCarrito = carritoDto.getDetallesCarritoDto();

        // Verifica si la lista de detalles del carrito es nula y crea una nueva si lo es
        if (detallesCarrito == null) {
            detallesCarrito = new ArrayList<>();
            carritoDto.setDetallesCarritoDto(detallesCarrito);
        }

        ProductoDto productoDto = productoService.ObtenerProductoPorId(id_producto);
        double precio = (productoDto.getPreciooferta() != null) ? productoDto.getPreciooferta() : productoDto.getPrecionormal();

        // Itera a través de los detalles del carrito para verificar si el producto ya existe
        for (DetallesCarritoDto detalle : detallesCarrito) {
            if (detalle.getProductodto().getId_producto().equals(id_producto)) {
                // El producto ya existe en el carrito, actualiza la cantidad y el total
                int nuevaCantidad = detalle.getCantidad() + cantidad;
                double nuevoTotal = nuevaCantidad * precio;
                detalle.setCantidad(nuevaCantidad);
                detalle.setTotal(nuevoTotal);

                // Actualiza el detalle del carrito en la base de datos
                logger.info("Producto repetitivo: " + detallesCarrito);
                detalleCarritoService.guardarDetalleCarrito(detalle, carritoDto);
                productoExistente = true;
                break;
            }
        }

        // Si el producto no existe en el carrito, crea un nuevo detalle de carrito
        if (!productoExistente) {
            DetallesCarritoDto detalleCarrito = new DetallesCarritoDto();
            detalleCarrito.setProductodto(productoDto);
            detalleCarrito.setCantidad(cantidad);
            detalleCarrito.setTotal(precio * cantidad);

            // Agrega el nuevo detalle a la lista
            detallesCarrito.add(detalleCarrito);
            logger.info("Añadiendo producto nuevo: " + detalleCarrito);
            // Guarda el nuevo detalle del carrito en la base de datos
            detalleCarritoService.guardarDetalleCarrito(detalleCarrito, carritoDto);
        }

        carritoService.CalcularTotalCarrito(carritoDto);
        model.addAttribute("carrito", carritoDto);
        logger.info("CARRITO: " + carritoDto);
        model.addAttribute("sesion", session.getAttribute("identificadordelusuario"));
        logger.info("SESION: " + session.getAttribute("identificadordelusuario"));
        model.addAttribute("detallesCarrito", detallesCarrito);
        logger.info("DETALLESCARRITO: " + detallesCarrito);

        return "redirect:/cart";
    }
 */
