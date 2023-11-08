package com.tiendaonline.service;

import com.tiendaonline.entity.PedidoEntity;
import com.tiendaonline.entity.UsuarioEntity;
import java.util.List;
import java.util.Optional;

public interface PedidoService {
    
    public List<PedidoEntity> listarTodosLosPedidos();
    public List<PedidoEntity> encontrarPorUsuario(UsuarioEntity usuarioEntity);
    public Optional<PedidoEntity> encontrarPorId(Long id_pedido);

}
