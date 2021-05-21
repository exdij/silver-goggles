package kc.airport_task;

import kc.airport_task.entities.Baggage;
import kc.airport_task.entities.Cargo;
import kc.airport_task.entities.repositories.BaggageRepository;
import kc.airport_task.entities.repositories.CargoRepository;
import kc.airport_task.entities.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;
import java.util.ListIterator;

public class CalcService {
    BaggageRepository baggageRepository;
    CargoRepository cargoRepository;
    FlightRepository flightRepository;
    CalcService(BaggageRepository baggageRepository, CargoRepository cargoRepository, FlightRepository flightRepository){
        this.baggageRepository = baggageRepository;
        this.cargoRepository = cargoRepository;
        this.flightRepository = flightRepository;
    }
    public Double[][] getWeight(Integer flightNumber, Date date){
        Long flightId = flightRepository.getIdForDateNumber(flightNumber,date);
        List<Baggage> baggage = baggageRepository.findBaggageByFlightId(flightId);
        List<Cargo> cargo = cargoRepository.findCargoByFlightId(flightId);
        Double[] baggageSum = calcBaggageWeight(baggage);
        Double[] cargoSum = calcCargoWeight(cargo);
        Double[] sum = {baggageSum[0] + cargoSum[0], baggageSum[1] + cargoSum[1]};
        return roundAll(new Double[][]{cargoSum,baggageSum,sum});
    }
    public Long[] getFlightsPieces(String Iata){
        Long departing = flightRepository.countDepartingFlightsByIata(Iata);
        Long arriving = flightRepository.countArrivalFlightsByIata(Iata);
        Long arrivingPiecesSum = getPiecesCount(flightRepository.getIdArrivalFlightsByIata(Iata));
        Long departingPieciesSum = getPiecesCount(flightRepository.getIdDepartingFlightsByIata(Iata));
        return new Long[]{departing,arriving,arrivingPiecesSum,departingPieciesSum};
    }


    private Long getPiecesCount(List<Long> ids){
        Long sum = 0L;
        for (Long id : ids) {
            sum += baggageRepository.sumPieciesByFlightId(id);
        }
        return sum;
    }

    private Double[] calcBaggageWeight(List<Baggage> baggage){
        Double sumKg = 0.0;
        Double sumLb = 0.0;

        ListIterator<Baggage> iter = baggage.listIterator();
        while(iter.hasNext()){
            Baggage next = iter.next();
            if(next.getWeightUnit().equals("kg")) {
                sumKg += next.getWeight();
            } else {
                sumLb += next.getWeight();
            }
        }
        Double sumKg_prev = sumKg;
        sumKg += toKg(sumLb);
        sumLb += toLb(sumKg_prev);

        return new Double[]{sumKg, sumLb};
    }
    private Double[] calcCargoWeight(List<Cargo> cargo){
        Double sumKg = 0.0;
        Double sumLb = 0.0;

        ListIterator<Cargo> iter = cargo.listIterator();
        while(iter.hasNext()){
            Cargo next = iter.next();
            if(next.getWeightUnit().equals("kg")) {
                sumKg += next.getWeight();
            } else {
                sumLb += next.getWeight();
            }
        }
        Double sumKg_prev = sumKg;
        sumKg += toKg(sumLb);
        sumLb += toLb(sumKg_prev);

        return new Double[]{sumKg, sumLb};
    }

    private Double toKg(Double inLb){
        return inLb * 0.45359237;
    }
    private Double toLb(Double inKg){
        return inKg * 2.20462262;
    }
    private Double[][] roundAll(Double[][] vals){
        for (int i = 0; i < vals.length; i++){
            for (int l = 0; l < vals[i].length; l++){
                vals[i][l] = round(vals[i][l]);
            }
        }
        return vals;
    }
    private Double round(Double val){
        BigDecimal bd = BigDecimal.valueOf(val);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
