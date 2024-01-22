package br.com.j38.springdataredis.mapper;

import br.com.j38.springdataredis.dto.VehicleDTO;
import br.com.j38.springdataredis.entity.Vehicle;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    private final ModelMapper modelMapper;

    public VehicleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Vehicle toEntity(VehicleDTO dto) {
        return modelMapper.map(dto, Vehicle.class);
    }

    public VehicleDTO toDTO(Vehicle entity) {
        return modelMapper.map(entity, VehicleDTO.class);
    }
}
