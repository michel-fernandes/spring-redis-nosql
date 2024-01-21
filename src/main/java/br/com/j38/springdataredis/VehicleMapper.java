package br.com.j38.springdataredis;

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
