package kc.airport_task;

import kc.airport_task.entities.CargoEntity;
import kc.airport_task.entities.Flight;
import kc.airport_task.entities.repositories.BaggageRepository;
import kc.airport_task.entities.repositories.CargoRepository;
import kc.airport_task.entities.repositories.FlightRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.ListIterator;

@RestController
public class Endpoints {
    @Autowired
    BaggageRepository baggageRepository;
    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    FlightRepository flightRepository;



    @PostMapping("/cargo")
    public void cargo(@RequestBody List<CargoEntity> cargos, @RequestHeader(value = "Trim", required = false) String trim) {
        if(trim == null || !trim.equals("true")){
            cargoRepository.truncateCargo();
            baggageRepository.truncateBaggage();
        }
        ListIterator<CargoEntity> iter = cargos.listIterator();
        while(iter.hasNext()){
            iter.next().save(baggageRepository,cargoRepository);
        }
    }
    @PostMapping("/flight")
    public void flight(@RequestBody List<Flight> flights, @RequestHeader(value = "Trim", required = false) String trim){
        if(trim == null || !trim.equals("true")){
            flightRepository.truncateFlight();
        }
        flightRepository.saveAll(flights);
    }
    @GetMapping("/weight")
    public ResponseEntity<String> getWeight(@RequestParam Integer flightNumber, @RequestParam Date date){
        CalcService calc = new CalcService(baggageRepository,cargoRepository,flightRepository);
        Double[][] sums = calc.getWeight(flightNumber, date);
            return ResponseEntity.ok()
            .body("Cargo weight for request flight is:\n"
                    + sums[0][0] + " in kg\n"
                    + sums[0][1] + " in lb\n"
                    + "Baggage weight for request flight is:\n"
                    + sums[1][0] + " in kg\n"
                    + sums[1][1] + " in lb\n"
                    + "Total weight for request flight is:\n"
                    + sums[2][0] + " in kg\n"
                    + sums[2][1] + " in lb\n");
        }

    @GetMapping("/iata")
    public ResponseEntity<String> getFlights(@RequestParam String Iata){
        CalcService calc = new CalcService(baggageRepository,cargoRepository,flightRepository);
        Long[] info = calc.getFlightsPieces(Iata);
        return ResponseEntity.ok()
                .body("Flights departing from the airport: " + info[0]
                + "\nFlights arriving to the airport: " + info[1]
                + "\nTotal number of baggage arriving to the airport: " + info[2]
                + "\nTotal number of baggage departing from the airport: " + info[3]);
    }


}
