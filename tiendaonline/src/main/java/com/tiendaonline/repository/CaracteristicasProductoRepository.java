package com.tiendaonline.repository;

import com.tiendaonline.entity.CaracteristicasProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicasProductoRepository extends JpaRepository<CaracteristicasProductoEntity, Long>{
    
}