package br.com.j38.springdataredis.repository;

import br.com.j38.springdataredis.entity.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface VehicleRedisRepository extends CrudRepository<Vehicle, String> {
    Set<Vehicle> findByStore(String store);
}
