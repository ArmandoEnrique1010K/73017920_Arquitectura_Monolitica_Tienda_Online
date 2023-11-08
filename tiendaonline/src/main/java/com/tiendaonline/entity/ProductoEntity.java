package com.tiendaonline.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
@Table(name = "producto")
@ToString

public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id_producto;
    @Column(name = "codigo", nullable = false)
    private String codigo;
    @Column(name = "nombre", length = 1000, nullable = false)
    private String nombre;
    @Column(name = "oferta")
    private Boolean oferta;
    @Column(name = "precionormal", nullable = false)
    private Double precionormal;
    @Column(name = "preciooferta")
    private Double preciooferta;
    @Column(name = "estado")
    private Boolean estado;
    
    @Column(name = "fechaeditado")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaeditado;
    
    // RELACIONES ENTRE TABLAS
    // Relación uno a uno con CaracteristicasProductoEntity.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caracteristicasproducto")
    private CaracteristicasProductoEntity caracteristicasProductoEntity;
    
    // Relación muchos a uno con CategoriaEntity.
    //@NotNull(message = "Este campo no puede estar en blanco")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoriaEntity;
    
    // Relación muchos a uno con MarcaEntity.
    //@NotNull(message = "Este campo no puede estar en blanco")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca")
    private MarcaEntity marcaEntity;

    // Relación uno a uno con ImagenProductoEntity.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_imagenproducto")
    private ImagenProductoEntity imagenProductoEntity;
    
    
    
    @OneToMany(mappedBy = "productoEntity", cascade = CascadeType.ALL, 
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetallesCarritoEntity> detallesCarritoEntity;

    
}
    // @NotNull @NotBlank(message = "Este campo no puede estar en blanco")
    // @NotNull @NotBlank(message = "Este campo no puede estar en blanco")
    // @NotNull(message = "Este campo no puede estar en blanco") @Min(value = 1, message = "El minimo valor debe ser 1")
