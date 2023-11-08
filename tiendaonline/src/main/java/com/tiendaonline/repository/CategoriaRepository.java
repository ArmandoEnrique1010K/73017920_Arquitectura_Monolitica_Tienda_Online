package com.tiendaonline.repository;

import com.tiendaonline.entity.CategoriaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long>{
    
    // LISTA DE CATEGORIAS CUYO ESTADO SEA TRUE
    @Query("SELECT c FROM CategoriaEntity c WHERE c.estado = true")
    List<CategoriaEntity> findAllByEstadoTrue();
    
    // SELECCIONA UNA CATEGORIA POR SU NOMBRE PROPORCIONADO COMO PARÁMETRO
    @Query("SELECT c FROM CategoriaEntity c WHERE c.nombre = :nombreCategoria")
    CategoriaEntity findByNombreParam(String nombreCategoria);

}
