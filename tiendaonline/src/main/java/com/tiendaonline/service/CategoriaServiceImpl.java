package com.tiendaonline.service;

import com.tiendaonline.dto.CategoriaDto;
import com.tiendaonline.dto.DatosCategoriaDto;
import com.tiendaonline.entity.CategoriaEntity;
import com.tiendaonline.exception.ObjetoNoEncontradoException;
import com.tiendaonline.repository.CategoriaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // LISTA DE CATEGORIAS
    @Override
    public List<DatosCategoriaDto> ListarCategorias() {
        
        List<CategoriaEntity> listCategoriaEntity = categoriaRepository.findAll();
        List<DatosCategoriaDto> list = new ArrayList<>();
        
        for (CategoriaEntity categoriaEntity : listCategoriaEntity) {
            DatosCategoriaDto datosCategoriaDto = DatosCategoriaDto.builder()
                    .id_categoria(categoriaEntity.getId_categoria())
                    .nombre(categoriaEntity.getNombre())
                    .estado(categoriaEntity.getEstado())
                    .build();
            list.add(datosCategoriaDto);
        }
        
        return list;

    }

    // OBTENER UNA CATEGORIA POR SU ID
    @Override
    public CategoriaDto ObtenerCategoriaPorId(Long id_categoria) {
        
        CategoriaEntity categoriaEntity = categoriaRepository.findById(id_categoria).orElse(null);
        
        if (categoriaEntity == null) {
            throw new ObjetoNoEncontradoException("La categoria con el ID: " + id_categoria + " no existe.");
        }
        
        CategoriaDto categoriaDto = CategoriaDto.builder()
                .id_categoria(categoriaEntity.getId_categoria())
                .nombre(categoriaEntity.getNombre())
                .build();

        return categoriaDto;
        
    }

    // GUARDAR UNA NUEVA CATEGORIA
    @Override
    public CategoriaDto GuardarCategoria(CategoriaDto categoriaDto) {

        CategoriaEntity categoriaEntity = CategoriaEntity.builder()
                .nombre(categoriaDto.getNombre())
                .estado(Boolean.TRUE)
                .build();

        categoriaRepository.save(categoriaEntity);
        categoriaDto.setId_categoria(categoriaEntity.getId_categoria());

        return categoriaDto;

    }

    // EDITAR UNA CATEGORIA (CAMBIAR DE NOMBRE)
    @Override
    public CategoriaDto CambiarNombreCategoria(Long id_categoria, CategoriaDto categoriaDto) {

        Optional<CategoriaEntity> categoriaOptional = categoriaRepository.findById(id_categoria);

        if (categoriaOptional.isPresent()) {
            
            CategoriaEntity categoriaEntity = categoriaOptional.get();
            
            categoriaEntity.setNombre(categoriaDto.getNombre());
            categoriaRepository.save(categoriaEntity);
            
            return categoriaDto;
            
        } else {
            
            throw new ObjetoNoEncontradoException("La categoría con el ID " + id_categoria + " no existe.");
            
        }
        
    }

    // INHABILITAR UNA CATEGORIA
    @Override
    public void EliminarCategoria(Long id_categoria) {

        CategoriaEntity categoriaEntity = categoriaRepository.findById(id_categoria).orElse(null);

        if (categoriaEntity != null) {
            
            categoriaEntity.setEstado(Boolean.FALSE);
            categoriaRepository.save(categoriaEntity);
            
        } else {
            
            throw new ObjetoNoEncontradoException("La categoria con el ID " + id_categoria + " no existe.");
            
        }
        
    }
    
    // HABILITAR UNA CATEGORIA
    @Override
    public void RestaurarCategoria(Long id_categoria) {

        CategoriaEntity categoriaEntity = categoriaRepository.findById(id_categoria).orElse(null);

        if (categoriaEntity != null) {

            categoriaEntity.setEstado(Boolean.TRUE);
            categoriaRepository.save(categoriaEntity);
            
        } else {
            
            throw new ObjetoNoEncontradoException("La categoria con el ID " + id_categoria + " no existe.");
            
        }

    }

    // LISTA DE CATEGORIAS CUYO ESTADO SEA TRUE
    @Override
    public List<CategoriaDto> ListarCategoriasByEstadoTrue() {

        List<CategoriaEntity> listCategoriaEntity = categoriaRepository.findAllByEstadoTrue();
        List<CategoriaDto> list = new ArrayList<>();

        for (CategoriaEntity categoriaEntity : listCategoriaEntity) {
            CategoriaDto categoriaDto = CategoriaDto.builder()
                    .id_categoria(categoriaEntity.getId_categoria())
                    .nombre(categoriaEntity.getNombre())
                    .build();
            list.add(categoriaDto);
        }

        return list;
        
    }

    // OBTENER UNA CATEGORIA POR SU NOMBRE
    @Override
    public CategoriaDto ObtenerCategoriaPorNombre(String nombreCategoria) {

        CategoriaEntity categoriaEntity = categoriaRepository.findByNombreParam(nombreCategoria);

        if (categoriaEntity == null) {
            throw new ObjetoNoEncontradoException("La categoría con el nombre " + nombreCategoria + " no existe.");
        }

        return CategoriaDto.builder()
                .id_categoria(categoriaEntity.getId_categoria())
                .nombre(categoriaEntity.getNombre())
                .build();

    }

}
