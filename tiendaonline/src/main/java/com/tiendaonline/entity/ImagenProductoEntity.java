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
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "imagenproducto")
public class ImagenProductoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagenproducto")
    private Long id_imagenproducto;
    @Column(name = "rutaimagen", nullable = false)
    private String rutaimagen;
    @Transient
    private MultipartFile imagen;
    
    // Relación uno a uno con ProductoEntity. Cada instancia de ImagenProductoEntity se relaciona con un producto.
    @OneToOne(mappedBy = "imagenProductoEntity", cascade = CascadeType.ALL, 
            orphanRemoval = true, fetch = FetchType.LAZY)
    private ProductoEntity productoEntity;

}


