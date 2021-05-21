package kc.airport_task.entities.repositories;

import kc.airport_task.entities.Flight;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface FlightRepository extends CrudRepository<Flight,Integer> {
    @Query("SELECT COUNT(f) FROM Flight f WHERE f.arrivalAirportIATACode = ?1")
    Long countArrivalFlightsByIata(String Iata);

    @Query("SELECT COUNT(f) FROM Flight f WHERE f.departureAirportIATACode = ?1")
    Long countDepartingFlightsByIata(String Iata);

    @Query("SELECT f.flightId FROM Flight f WHERE f.departureAirportIATACode = ?1")
    List<Long> getIdDepartingFlightsByIata(String Iata);

    @Query("SELECT f.flightId FROM Flight f WHERE f.arrivalAirportIATACode = ?1")
    List<Long> getIdArrivalFlightsByIata(String Iata);

    @Query("SELECT f.flightId FROM Flight f WHERE f.flightNumber = ?1 AND f.departureDate = ?2")
    Long getIdForDateNumber(Integer flightNumber, Date date);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE flight", nativeQuery = true)
    void truncateFlight();

}
