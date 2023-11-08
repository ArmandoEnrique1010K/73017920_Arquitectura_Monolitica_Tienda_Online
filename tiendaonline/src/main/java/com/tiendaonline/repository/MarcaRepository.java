package com.tiendaonline.repository;

import com.tiendaonline.entity.MarcaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<MarcaEntity, Long>{
    
    // LISTA DE MARCAS CUYO ESTADO SEA TRUE
    @Query("SELECT m FROM MarcaEntity m WHERE m.estado = true")
    List<MarcaEntity> findAllByEstadoTrue();
        
    // LISTA DE MARCAS DISTINTAS QUE PERTENEZCAN A LA MISMA CATEGORIA, TOMA COMO PARAMETRO EL NOMBRE DE LA CATEGORIA
    @Query("SELECT DISTINCT m FROM MarcaEntity m JOIN m.productoEntity p JOIN p.categoriaEntity c "
            + "WHERE c.nombre = :nombreCategoria AND m.estado = true AND p.estado = true")
    List<MarcaEntity> findAllDistinctByCategoriaGroupAndEstadoTrue(@Param("nombreCategoria") String nombreCategoria);

}