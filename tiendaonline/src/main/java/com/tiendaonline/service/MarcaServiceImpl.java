package com.tiendaonline.service;

import com.tiendaonline.dto.DatosMarcaDto;
import com.tiendaonline.dto.MarcaDto;
import com.tiendaonline.entity.MarcaEntity;
import com.tiendaonline.exception.ObjetoNoEncontradoException;
import com.tiendaonline.repository.MarcaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    // LISTA DE MARCAS
    @Override
    public List<DatosMarcaDto> ListarMarcas() {
        
        List<MarcaEntity> listMarcaEntity = marcaRepository.findAll();
        List<DatosMarcaDto> list = new ArrayList<>();
        
        for (MarcaEntity marcaEntity : listMarcaEntity) {
            DatosMarcaDto datosMarcaDto = DatosMarcaDto.builder()
                    .id_marca(marcaEntity.getId_marca())
                    .nombre(marcaEntity.getNombre())
                    .estado(marcaEntity.getEstado())
                    .build();
            list.add(datosMarcaDto);
        }

        return list;

    }

    // OBTENER UNA MARCA POR SU ID
    @Override
    public MarcaDto ObtenerMarcaPorId(Long id_marca) {

        MarcaEntity marcaEntity = marcaRepository.findById(id_marca).orElse(null);

        if (marcaEntity == null) {
            throw new ObjetoNoEncontradoException("La marca con el ID: " + id_marca + " no existe.");
        }

        MarcaDto marcaDto = MarcaDto.builder()
                .id_marca(marcaEntity.getId_marca())
                .nombre(marcaEntity.getNombre())
                .build();

        return marcaDto;
        
    }

    // GUARDAR UNA NUEVA MARCA
    @Override
    public MarcaDto GuardarMarca(MarcaDto marcaDto) {

        MarcaEntity marcaEntity = MarcaEntity.builder()
                .nombre(marcaDto.getNombre())
                .estado(Boolean.TRUE)
                .build();

        marcaRepository.save(marcaEntity);
        marcaDto.setId_marca(marcaEntity.getId_marca());

        return marcaDto;

    }

    // EDITAR UNA MARCA (CAMBIAR DE NOMBRE)
    @Override
    public MarcaDto CambiarNombreMarca(Long id_marca, MarcaDto marcaDto) {

        Optional<MarcaEntity> marcaOptional = marcaRepository.findById(id_marca);

        if (marcaOptional.isPresent()) {

            MarcaEntity marcaEntity = marcaOptional.get();

            marcaEntity.setNombre(marcaDto.getNombre());
            marcaRepository.save(marcaEntity);

            return marcaDto;

        } else {
            
            throw new ObjetoNoEncontradoException("La marca con el ID " + id_marca + "no existe.");
            
        }

    }

    // INHABILITAR UNA MARCA
    @Override
    public void EliminarMarca(Long id_marca) {

        MarcaEntity marcaEntity = marcaRepository.findById(id_marca).orElse(null);

        if (marcaEntity != null) {

            marcaEntity.setEstado(Boolean.FALSE);
            marcaRepository.save(marcaEntity);

        } else {
            
            throw new ObjetoNoEncontradoException("La marca con el ID " + id_marca + " no existe.");
            
        }

    }
    
    // HABILITAR UNA MARCA
    @Override
    public void RestaurarMarca(Long id_marca) {
        
        MarcaEntity marcaEntity = marcaRepository.findById(id_marca).orElse(null);

        if (marcaEntity != null) {

            marcaEntity.setEstado(Boolean.TRUE);
            marcaRepository.save(marcaEntity);

        } else {
            
            throw new ObjetoNoEncontradoException("La marca con el ID " + id_marca + " no existe.");
            
        }

    }
    
    // LISTA DE MARCAS CUYO ESTADO SEA TRUE
    @Override
    public List<MarcaDto> ListarMarcasByEstadoTrue() {

        List<MarcaEntity> listMarcaEntity = marcaRepository.findAllByEstadoTrue();
        List<MarcaDto> list = new ArrayList<>();

        for (MarcaEntity marcaEntity : listMarcaEntity) {
            MarcaDto marcaDto = MarcaDto.builder()
                    .id_marca(marcaEntity.getId_marca())
                    .nombre(marcaEntity.getNombre())
                    .build();
            list.add(marcaDto);
        }

        return list;

    }

    // LISTA DE MARCAS DISTINTAS QUE PERTENEZCAN A LA MISMA CATEGORIA POR EL NOMBRE DE LA CATEGORIA
    @Override
    public List<MarcaDto> ListarDistintasMarcasByGrupoCategoriaAndEstadoTrue(String nombreCategoria) {

        List<MarcaEntity> listMarcaEntity = marcaRepository.findAllDistinctByCategoriaGroupAndEstadoTrue(nombreCategoria);
        List<MarcaDto> list = new ArrayList<>();

        if (listMarcaEntity.isEmpty()) {
            throw new ObjetoNoEncontradoException("No se encontraron marcas asociadas a la categoría " + nombreCategoria);
        }

        for (MarcaEntity marcaEntity : listMarcaEntity) {
            MarcaDto marcaDto = MarcaDto.builder()
                    .id_marca(marcaEntity.getId_marca())
                    .nombre(marcaEntity.getNombre())
                    .build();
            list.add(marcaDto);
        }

        return list;

    }

}
