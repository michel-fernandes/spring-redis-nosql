package br.com.j38.springdataredis.service;

import br.com.j38.springdataredis.entity.Vehicle;
import br.com.j38.springdataredis.repository.VehicleRedisRepository;
import br.com.j38.springdataredis.repository.VehicleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VehicleService {
    VehicleRedisRepository vehicleRedisRepository;
    VehicleRepository vehicleRepository;

    @Transactional
    public Vehicle createOrUpdateVehicle(Vehicle vehicle) {
        //setCreatedAt() com a data UTC atual
        vehicle.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        vehicleRedisRepository.save(savedVehicle);
        return savedVehicle;
    }

    @Transactional
    public Vehicle updateVehicle(Vehicle vehicleChanged) {
        //Busca o veículo no Redis, se não encontrar busca no banco de dados
        Optional<Vehicle> existingVehicle = vehicleRedisRepository.findById(vehicleChanged.getPlate());

        if (existingVehicle.isEmpty())
            existingVehicle = vehicleRepository.findById(vehicleChanged.getPlate());

        if (existingVehicle.isPresent()) {
            var vehicleToUpdate = new Vehicle();
            BeanUtils.copyProperties(vehicleChanged, vehicleToUpdate);
            vehicleToUpdate.setCreatedAt(existingVehicle.get().getCreatedAt());
            vehicleToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            Vehicle updatedVehicle = vehicleRepository.save(vehicleToUpdate);
            vehicleRedisRepository.save(updatedVehicle);
            return updatedVehicle;
        }

        return null;
    }

    @Transactional
    public Vehicle findByPlate(String plate) {
        return vehicleRedisRepository.findById(plate)
                .orElseGet(() -> vehicleRepository.findById(plate).orElse(null));
    }

    @Transactional
    public List<Vehicle> findAllVehicles() {
        //retornar todos os veículos do Redis, caso não tenha nenhum, retornar todos do banco de dados
        Iterable<Vehicle> vehicles = vehicleRedisRepository.findAll();

        if (vehicles.iterator().hasNext()) {
            List<Vehicle> vehicleList = new ArrayList<>();
            vehicles.forEach(vehicleList::add);
            return vehicleList;
        } else
            return vehicleRepository.findAll();
    }

    public List<Vehicle> findVehiclesByStore(String store) {
        //retornar todos os veículos do Redis com store, caso não tenha nenhum, retornar todos do banco de dados
        Set<Vehicle> vehicles = vehicleRedisRepository.findByStore(store);

        if (vehicles.iterator().hasNext()) {
            return new ArrayList<>(vehicles);
        } else
            return vehicleRepository.findByStore(store);
    }
}
