package kc.airport_task.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Baggage {
    @Id
    private Integer id;
    private Integer flightId;
    private Integer weight;
    private String weightUnit;
    private Integer pieces;
}
