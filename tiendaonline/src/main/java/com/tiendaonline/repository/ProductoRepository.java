package com.tiendaonline.repository;

import com.tiendaonline.entity.ProductoEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    // PARA USUARIOS (SOLAMENTE PRODUCTOS HABILITADOS)
    // LISTA LOS PRODUCTOS QUE CUMPLAN CON LA CONDICIÓN POR PARAMETROS:
    // categoriaId: ID de la categoria, puede ser todas o solamente 1
    // marcaIds: Lista de IDs de la marca, puede ser todas o varias a la vez
    // minPrecio: Limita el precio minimo de los productos, toma el precio normal o el precio de oferta si esta de oferta
    // maxPrecio: Limita el precio maximo de los productos, toma el precio normal o el precio de oferta si esta de oferta
    // enOferta: Productos en oferta
    // palabraClave: Productos que coincidan con la "palabraClave" en el nombre del producto   
    @Query(value = "SELECT p FROM ProductoEntity p JOIN p.categoriaEntity c JOIN p.marcaEntity m "
            + "WHERE (:categoriaId IS NULL OR c.id_categoria = :categoriaId) "
            + "AND (COALESCE(:marcaIds, NULL) IS NULL OR m.id_marca IN :marcaIds) "
            + "AND ((:minPrecio IS NULL AND :maxPrecio IS NULL) OR "
            + "     (p.oferta = true AND (:minPrecio IS NULL OR p.preciooferta >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.preciooferta <= :maxPrecio)) OR "
            + "     (p.oferta = false AND (:minPrecio IS NULL OR p.precionormal >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.precionormal <= :maxPrecio))) "
            + "AND (:enOferta IS NULL OR p.oferta = :enOferta) "
            + "AND (:palabraClave IS NULL OR CONCAT(' ', p.nombre) LIKE %:palabraClave%) "
            + "AND p.estado = true "
            + "AND c.estado = true "
            + "AND m.estado = true "
            + "ORDER BY p.id_producto DESC"
    )
    List<ProductoEntity> findAllByEstadoTrueAndParams(
            @Param("categoriaId") Long categoriaId,
            @Param("marcaIds") List<Long> marcaIds,
            @Param("minPrecio") Double minPrecio,
            @Param("maxPrecio") Double maxPrecio,
            @Param("enOferta") Boolean enOferta,
            @Param("palabraClave") String palabraClave
    );

    // CUENTA LOS PRODUCTOS QUE CUMPLAN CON LA CONDICIÓN POR PARAMETROS (IGUAL QUE LA ANTERIOR):
    @Query("SELECT COUNT(p) FROM ProductoEntity p JOIN p.categoriaEntity c JOIN p.marcaEntity m "
            + "WHERE (:categoriaId IS NULL OR c.id_categoria = :categoriaId) "
            + "AND (COALESCE(:marcaIds, NULL) IS NULL OR m.id_marca IN :marcaIds) "
            + "AND ((:minPrecio IS NULL AND :maxPrecio IS NULL) OR "
            + "     (p.oferta = true AND (:minPrecio IS NULL OR p.preciooferta >= :minPrecio) "
            + "             AND (:maxPrecio IS NULL OR p.preciooferta <= :maxPrecio)) OR "
            + "     (p.oferta = false AND (:minPrecio IS NULL OR p.precionormal >= :minPrecio) "
            + "             AND (:maxPrecio IS NULL OR p.precionormal <= :maxPrecio))) "
            + "AND (:enOferta IS NULL OR p.oferta = :enOferta) "
            + "AND (:palabraClave IS NULL OR CONCAT(' ', p.nombre) LIKE %:palabraClave%) "
            + "AND p.estado = true "
            + "AND c.estado = true "
            + "AND m.estado = true"
    )
    Long countAllByEstadoTrueAndParams(
            @Param("categoriaId") Long categoriaId,
            @Param("marcaIds") List<Long> marcaIds,
            @Param("minPrecio") Double minPrecio,
            @Param("maxPrecio") Double maxPrecio,
            @Param("enOferta") Boolean enOferta,
            @Param("palabraClave") String palabraClave
    );

    // ----------------------------------------------------------------------------------
    // PARA ADMINISTRADORES (PRODUCTOS HABILITADOS E INHABILITADOS)
    // LISTA LOS PRODUCTOS QUE CUMPLAN CON LA CONDICIÓN POR PARAMETROS:
    // A DIFERENCIA DEL PRIMER @QUERY, AQUI SELECCIONA TODOS LOS PRODUCTOS TANTO HABILITADOS COMO INHABILITADOS
    @Query(value = "SELECT p FROM ProductoEntity p JOIN p.categoriaEntity c JOIN p.marcaEntity m "
            + "WHERE (:categoriaId IS NULL OR c.id_categoria = :categoriaId) "
            + "AND (COALESCE(:marcaIds, NULL) IS NULL OR m.id_marca IN :marcaIds) "
            + "AND ((:minPrecio IS NULL AND :maxPrecio IS NULL) OR "
            + "     (p.oferta = true AND (:minPrecio IS NULL OR p.preciooferta >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.preciooferta <= :maxPrecio)) OR "
            + "     (p.oferta = false AND (:minPrecio IS NULL OR p.precionormal >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.precionormal <= :maxPrecio))) "
            + "AND (:enOferta IS NULL OR p.oferta = :enOferta) "
            + "AND (:palabraClave IS NULL OR CONCAT(' ', p.nombre) LIKE %:palabraClave%) "
            + "ORDER BY p.id_producto DESC"
    )
    List<ProductoEntity> findAllByParams(
            @Param("categoriaId") Long categoriaId,
            @Param("marcaIds") List<Long> marcaIds,
            @Param("minPrecio") Double minPrecio,
            @Param("maxPrecio") Double maxPrecio,
            @Param("enOferta") Boolean enOferta,
            @Param("palabraClave") String palabraClave
    );

    // CUENTA LOS PRODUCTOS QUE CUMPLAN CON LA CONDICIÓN POR PARAMETROS:
    @Query("SELECT COUNT(p) FROM ProductoEntity p JOIN p.categoriaEntity c JOIN p.marcaEntity m "
            + "WHERE (:categoriaId IS NULL OR c.id_categoria = :categoriaId) "
            + "AND (COALESCE(:marcaIds, NULL) IS NULL OR m.id_marca IN :marcaIds) "
            + "AND ((:minPrecio IS NULL AND :maxPrecio IS NULL) OR "
            + "     (p.oferta = true AND (:minPrecio IS NULL OR p.preciooferta >= :minPrecio) "
            + "             AND (:maxPrecio IS NULL OR p.preciooferta <= :maxPrecio)) OR "
            + "     (p.oferta = false AND (:minPrecio IS NULL OR p.precionormal >= :minPrecio) "
            + "             AND (:maxPrecio IS NULL OR p.precionormal <= :maxPrecio))) "
            + "AND (:enOferta IS NULL OR p.oferta = :enOferta) "
            + "AND (:palabraClave IS NULL OR CONCAT(' ', p.nombre) LIKE %:palabraClave%)"
    )
    Long countAllByParams(
            @Param("categoriaId") Long categoriaId,
            @Param("marcaIds") List<Long> marcaIds,
            @Param("minPrecio") Double minPrecio,
            @Param("maxPrecio") Double maxPrecio,
            @Param("enOferta") Boolean enOferta,
            @Param("palabraClave") String palabraClave
    );

    // PARA USUARIOS (SOLAMENTE PRODUCTOS HABILITADOS)
    // PAGINAR LOS PRODUCTOS QUE CUMPLAN CON LA CONDICIÓN POR PARAMETROS:
    // categoriaId: ID de la categoria, puede ser todas o solamente 1
    // marcaIds: Lista de IDs de la marca, puede ser todas o varias a la vez
    // minPrecio: Limita el precio minimo de los productos, toma el precio normal o el precio de oferta si esta de oferta
    // maxPrecio: Limita el precio maximo de los productos, toma el precio normal o el precio de oferta si esta de oferta
    // enOferta: Productos en oferta
    // palabraClave: Productos que coincidan con la "palabraClave" en el nombre del producto   
    @Query(value = "SELECT p FROM ProductoEntity p JOIN p.categoriaEntity c JOIN p.marcaEntity m "
            + "WHERE (:categoriaId IS NULL OR c.id_categoria = :categoriaId) "
            + "AND (COALESCE(:marcaIds, NULL) IS NULL OR m.id_marca IN :marcaIds) "
            + "AND ((:minPrecio IS NULL AND :maxPrecio IS NULL) OR "
            + "     (p.oferta = true AND (:minPrecio IS NULL OR p.preciooferta >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.preciooferta <= :maxPrecio)) OR "
            + "     (p.oferta = false AND (:minPrecio IS NULL OR p.precionormal >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.precionormal <= :maxPrecio))) "
            + "AND (:enOferta IS NULL OR p.oferta = :enOferta) "
            + "AND (:palabraClave IS NULL OR CONCAT(' ', p.nombre) LIKE %:palabraClave%) "
            + "AND p.estado = true "
            + "AND c.estado = true "
            + "AND m.estado = true "
            + "ORDER BY p.id_producto DESC"
    )

    // LISTA LOS PRODUCTOS QUE CUMPLAN CON LA CONDICIÓN POR PARAMETROS:
    // A DIFERENCIA DEL PRIMER @QUERY, AQUI SELECCIONA TODOS LOS PRODUCTOS TANTO HABILITADOS COMO INHABILITADOS
    Page<ProductoEntity> pageAllByEstadoTrueAndParams(
            @Param("categoriaId") Long categoriaId,
            @Param("marcaIds") List<Long> marcaIds,
            @Param("minPrecio") Double minPrecio,
            @Param("maxPrecio") Double maxPrecio,
            @Param("enOferta") Boolean enOferta,
            @Param("palabraClave") String palabraClave,
            Pageable pageable
    );

    // PAGINAR LOS PRODUCTOS QUE CUMPLAN CON LA CONDICIÓN POR PARAMETROS:
    // A DIFERENCIA DEL PRIMER @QUERY, AQUI SELECCIONA TODOS LOS PRODUCTOS TANTO HABILITADOS COMO INHABILITADOS
    @Query(value = "SELECT p FROM ProductoEntity p JOIN p.categoriaEntity c JOIN p.marcaEntity m "
            + "WHERE (:categoriaId IS NULL OR c.id_categoria = :categoriaId) "
            + "AND (COALESCE(:marcaIds, NULL) IS NULL OR m.id_marca IN :marcaIds) "
            + "AND ((:minPrecio IS NULL AND :maxPrecio IS NULL) OR "
            + "     (p.oferta = true AND (:minPrecio IS NULL OR p.preciooferta >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.preciooferta <= :maxPrecio)) OR "
            + "     (p.oferta = false AND (:minPrecio IS NULL OR p.precionormal >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.precionormal <= :maxPrecio))) "
            + "AND (:enOferta IS NULL OR p.oferta = :enOferta) "
            + "AND (:palabraClave IS NULL OR CONCAT(' ', p.nombre) LIKE %:palabraClave%) "
            + "ORDER BY p.fechaeditado DESC"
    )
    Page<ProductoEntity> pageAllByParams(
            @Param("categoriaId") Long categoriaId,
            @Param("marcaIds") List<Long> marcaIds,
            @Param("minPrecio") Double minPrecio,
            @Param("maxPrecio") Double maxPrecio,
            @Param("enOferta") Boolean enOferta,
            @Param("palabraClave") String palabraClave,
            Pageable pageable
    );

    
    
    
    
    
    
    
    
    
    
    
    
    /*
    // PAGINAR Y ORDENAR AL MISMO TIEMPO
    @Query(value = "SELECT p FROM ProductoEntity p JOIN p.categoriaEntity c JOIN p.marcaEntity m "
            + "WHERE (:categoriaId IS NULL OR c.id_categoria = :categoriaId) "
            + "AND (COALESCE(:marcaIds, NULL) IS NULL OR m.id_marca IN :marcaIds) "
            + "AND ((:minPrecio IS NULL AND :maxPrecio IS NULL) OR "
            + "     (p.oferta = true AND (:minPrecio IS NULL OR p.preciooferta >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.preciooferta <= :maxPrecio)) OR "
            + "     (p.oferta = false AND (:minPrecio IS NULL OR p.precionormal >= :minPrecio) "
            + "                     AND (:maxPrecio IS NULL OR p.precionormal <= :maxPrecio))) "
            + "AND (:enOferta IS NULL OR p.oferta = :enOferta) "
            + "AND (:palabraClave IS NULL OR CONCAT(' ', p.nombre) LIKE %:palabraClave%) "
            + "ORDER BY"
            + " CASE "
            + "  WHEN :ordenarPor = 'nombre' THEN p.nombre "
            + "  WHEN :ordenarPor = 'id' THEN p.id_producto "
            + "  WHEN :ordenarPor = 'marca' THEN p.marcaEntity.nombre " // Utiliza "p.marcaEntity.nombre" para ordenar por nombre de marca
            + "  ELSE p.fechaeditado "
            + " END DESC" 
    )
    Page<ProductoEntity> pageAndOrderAllByParams(
            @Param("categoriaId") Long categoriaId,
            @Param("marcaIds") List<Long> marcaIds,
            @Param("minPrecio") Double minPrecio,
            @Param("maxPrecio") Double maxPrecio,
            @Param("enOferta") Boolean enOferta,
            @Param("palabraClave") String palabraClave,
            @Param("ordenarPor") String ordenarPor,
            Pageable pageable
    );
*/

}
