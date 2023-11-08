package com.tiendaonline.service;

import com.tiendaonline.dto.CarritoDto;
import com.tiendaonline.dto.DatosUsuarioDto;


public interface CarritoService {
    /*
    public CarritoEntity guardarCarrito(CarritoEntity carritoEntity);
    public CarritoEntity obtenerCarritoPorUsuario(UsuarioEntity usuario);
    public CarritoEntity obtenerCarritoConDetallesPorUsuario(UsuarioEntity usuario);
    public void CalcularTotalCarrito(CarritoEntity carrito);
    */
    public CarritoDto guardarCarrito(CarritoDto carritoDto, DatosUsuarioDto datosUsuarioDto);
    public CarritoDto obtenerCarritoPorUsuario(DatosUsuarioDto datosUsuarioDto);
    public void CalcularTotalCarrito(CarritoDto carritoDto);

    
}
