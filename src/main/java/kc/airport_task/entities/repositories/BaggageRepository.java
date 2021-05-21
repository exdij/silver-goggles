package kc.airport_task.entities.repositories;

import kc.airport_task.entities.Baggage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BaggageRepository extends CrudRepository<Baggage, Integer> {
    @Query("SELECT b FROM Baggage b WHERE b.flightId = ?1")
    List<Baggage> findBaggageByFlightId(Long flightId);

    @Query("SELECT SUM(b.pieces) FROM Baggage b WHERE b.flightId = ?1")
    Long sumPieciesByFlightId(Long flightId);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE baggage", nativeQuery = true)
    void truncateBaggage();
}
