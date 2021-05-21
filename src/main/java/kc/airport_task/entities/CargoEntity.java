package kc.airport_task.entities;

import kc.airport_task.entities.repositories.BaggageRepository;
import kc.airport_task.entities.repositories.CargoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ListIterator;

@Setter
@Getter
public class CargoEntity {
    private Long flightId;
    private List<Baggage> baggage;
    private List<Cargo> cargo;

    public void save(BaggageRepository baggageRepository, CargoRepository cargoRepository){
        setIdSaveBaggage(baggageRepository);
        setIdSaveCargo(cargoRepository);
    }

    private void setIdSaveBaggage(BaggageRepository baggageRepository){
        ListIterator<Baggage> baggageIter = baggage.listIterator();
        while(baggageIter.hasNext()){
            baggageIter.next().setFlightId(flightId);
        }
        baggageRepository.saveAll(baggage);
    }
    private void setIdSaveCargo(CargoRepository cargoRepository){
        ListIterator<Cargo> cargoIter = cargo.listIterator();
        while(cargoIter.hasNext()){
            cargoIter.next().setFlightId(flightId);
        }
        cargoRepository.saveAll(cargo);

    }
}
