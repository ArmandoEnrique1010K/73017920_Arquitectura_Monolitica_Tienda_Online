package com.tiendaonline.service;

import com.tiendaonline.dto.DatosUsuarioDto;
import com.tiendaonline.entity.RolEntity;
import com.tiendaonline.entity.UsuarioEntity;
import com.tiendaonline.exception.ObjetoNoEncontradoException;
import com.tiendaonline.repository.RolRepository;
import com.tiendaonline.repository.UsuarioRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    HttpSession session;

    @Override
    public DatosUsuarioDto GuardarUsuario(DatosUsuarioDto datosUsuarioDto) {
        
        // Crear una entidad de rol "USER" para el nuevo usuario
        RolEntity rolEntity = RolEntity.builder()
                .nombre("USER")
                .build();
        
        // Almacenar la entidad del rol en la base de datos
        rolEntity = rolRepository.save(rolEntity);

        // Construir una entidad de usuario con los datos proporcionados
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .nombre(datosUsuarioDto.getNombre())
                .apellido(datosUsuarioDto.getApellido())
                .edad(datosUsuarioDto.getEdad())
                .dni(datosUsuarioDto.getDni())
                .sexo(datosUsuarioDto.getSexo())
                .email(datosUsuarioDto.getEmail())
                // Encriptar la contraseña para almacenarla de manera segura
                .password(passwordEncoder.encode(datosUsuarioDto.getPassword()))
                // Asignar el rol "USER" al nuevo usuario
                .roles(Arrays.asList(rolEntity))
                .build();

        // Guardar la entidad del usuario en la base de datos
        usuarioEntity = usuarioRepository.save(usuarioEntity);
        // Actualizar el objeto datosUsuarioDto con el ID del usuario creado
        datosUsuarioDto.setId_usuario(usuarioEntity.getId_usuario());
        // Devolver el objeto datosUsuarioDto actualizado
        return datosUsuarioDto;

    }

    

    @Override
    public DatosUsuarioDto ObtenerUsuarioPorEmail(String email) {

        // Buscar un usuario por su dirección de correo electrónico en el repositorio
        UsuarioEntity usuarioEntity = usuarioRepository.findUserByEmail(email);

        // Verificar si el usuario no fue encontrado en el repositorio
        if (usuarioEntity == null) {
            // Lanzar una excepción en caso de que el usuario no exista
            throw new ObjetoNoEncontradoException("El usuario no existe");
        }

        // Construir un objeto DatosUsuarioDto utilizando los datos del usuario encontrado
        DatosUsuarioDto datosUsuarioDto = DatosUsuarioDto.builder()
                .id_usuario(usuarioEntity.getId_usuario())
                .nombre(usuarioEntity.getNombre())
                .apellido(usuarioEntity.getApellido())
                .email(usuarioEntity.getEmail())
                .build();
        // Devolver el objeto DatosUsuarioDto con la información del usuario encontrado
        return datosUsuarioDto;

    }

    @Override
    public DatosUsuarioDto ObtenerUsuarioPorId(Long id_usuario) {

        // Buscar un usuario por su ID en el repositorio
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id_usuario).orElse(null);
        
        // Verificar si el usuario no fue encontrado en el repositorio
        if (usuarioEntity == null) {
            // Lanzar una excepción en caso de que el usuario no exista
            throw new ObjetoNoEncontradoException("El usuario no existe");
        }
        
        // Construir un objeto DatosUsuarioDto utilizando los datos del usuario encontrado
        DatosUsuarioDto datosUsuarioDto = DatosUsuarioDto.builder()
                .id_usuario(usuarioEntity.getId_usuario())
                .nombre(usuarioEntity.getNombre())
                .apellido(usuarioEntity.getApellido())
                .email(usuarioEntity.getEmail())
                .build();
        
        // Devolver el objeto DatosUsuarioDto con la información del usuario encontrado
        return datosUsuarioDto;
    }
    
    
    // VERIFICA SI YA EXISTE UN USUARIO REGISTRADO CON EL EMAIL PROPORCIONADO POR EL USUARIO
    @Override
    public boolean ExisteUsuarioPorEmail(String email) {
        
        // Utiliza el repositorio para buscar un usuario por su correo electrónico
        UsuarioEntity usuario = usuarioRepository.findUserByEmail(email);
        
        // Si se encuentra un usuario con el mismo correo, entonces ya existe
        return usuario != null;
    
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UsuarioEntity usuarioEntity = usuarioRepository.findUserByEmail(username);
        if (usuarioEntity == null) {
            throw new UsernameNotFoundException("Usuario o contraseña invalido(a)");
        }

        /* Convertir la lista de roles a una lista de cadenas (nombres de roles) */
        List<String> roles = usuarioEntity.getRoles().stream()
                .map(role -> role.getNombre())
                .collect(Collectors.toList());

        // Almacenar el ID del usuario en la sesión
        session.setAttribute("identificadordelusuario", usuarioEntity.getId_usuario().toString());

        // Configurar los roles correctamente
        return User.withUsername(usuarioEntity.getEmail())
                .password(usuarioEntity.getPassword())
                .roles(roles.toArray(String[]::new))
                .build();

    }

}


        // return new User(usuarioEntity.getEmail(), usuarioEntity.getPassword(), mapearAutoridadesARoles(usuarioEntity.getRoles()));
/*
    @Override
    public boolean ValidarCredenciales(String email, String password) {
        // Buscar un usuario en la base de datos con el correo proporcionado
        UsuarioEntity usuario = usuarioRepository.findUserByEmail(email);

        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            // La contraseña coincide con la contraseña almacenada en la base de datos
            return true;
        }

        // Las credenciales no son válidas
        return false;
    }
*/