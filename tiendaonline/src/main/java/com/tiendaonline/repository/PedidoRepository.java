package com.tiendaonline.repository;

import com.tiendaonline.entity.PedidoEntity;
import com.tiendaonline.entity.UsuarioEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository {
    // Utilizando @Query para buscar pedidos por usuario
    @Query("SELECT pe FROM PedidoEntity pe WHERE pe.carritoEntity.usuarioEntity = :usuarioEntity")
    List<PedidoEntity> findPedidosByUsuario(UsuarioEntity usuarioEntity);

}
