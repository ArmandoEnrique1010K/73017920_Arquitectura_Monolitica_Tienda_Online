package com.tiendaonline.repository;

import com.tiendaonline.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long>{
    
    // Consulta personalizada para buscar un rol por su nombre
    /*
    @Query("SELECT r FROM RolEntity r WHERE r.nombre = :nombre")
    RolEntity findByNombre(@Param("nombre") String nombre);
*/
}
