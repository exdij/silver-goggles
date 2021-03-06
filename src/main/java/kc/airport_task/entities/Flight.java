package kc.airport_task.entities;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;


@Setter
@Getter
@Entity
public class Flight {
    @Id private Integer flightId;
    private Integer flightNumber;
    private String departureAirportIATACode;
    private String arrivalAirportIATACode;
    private Date departureDate;
}
