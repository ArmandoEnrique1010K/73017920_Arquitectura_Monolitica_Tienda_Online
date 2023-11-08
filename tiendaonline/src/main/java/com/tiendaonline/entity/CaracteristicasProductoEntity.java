package com.tiendaonline.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "caracteristicasproducto")
@ToString

public class CaracteristicasProductoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristicasproducto")
    private Long id_caracteristicasproducto;
    @Column(name = "descripcion", length = 2000, nullable = false)
    private String descripcion;
    @Column(name = "fichatecnica", columnDefinition = "MEDIUMTEXT", nullable = false)
    private String fichatecnica;
    
    // Relación uno a uno con ProductoEntity. Cada instancia de CaracteristicasProductoEntity se relaciona con un producto.
    @OneToOne(mappedBy = "caracteristicasProductoEntity", cascade = CascadeType.ALL, 
            orphanRemoval = true, fetch = FetchType.LAZY)
    private ProductoEntity productoEntity;

}
