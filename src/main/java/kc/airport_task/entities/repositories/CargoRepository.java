package kc.airport_task.entities.repositories;

import kc.airport_task.entities.Cargo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CargoRepository extends CrudRepository<Cargo, Integer> {
    @Query("SELECT c FROM Cargo c WHERE c.flightId = ?1")
    List<Cargo> findCargoByFlightId(Long flightId);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE cargo", nativeQuery = true)
    void truncateCargo();
}
