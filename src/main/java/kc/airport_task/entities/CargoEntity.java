package kc.airport_task.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CargoEntity {
    private Integer flightId;
    private List<Baggage> baggage;
    private List<Cargo> cargo;
}
