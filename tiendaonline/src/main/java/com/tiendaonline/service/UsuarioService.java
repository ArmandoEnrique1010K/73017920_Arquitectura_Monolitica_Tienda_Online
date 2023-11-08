package com.tiendaonline.service;

import com.tiendaonline.dto.DatosUsuarioDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService{
    
    // Registra un nuevo usuario en la base de datos.
    public DatosUsuarioDto GuardarUsuario(DatosUsuarioDto datosUsuarioDto);
    // Obtiene un usuario por su dirección de correo electrónico.
    public DatosUsuarioDto ObtenerUsuarioPorEmail(String email);
    // Obtiene un usuario por su ID.
    public DatosUsuarioDto ObtenerUsuarioPorId(Long id_usuario);
    // Verifica si ya existe un usuario registro con el correo.
    public boolean ExisteUsuarioPorEmail(String email);

}
//    public boolean ValidarCredenciales(String email, String password);
