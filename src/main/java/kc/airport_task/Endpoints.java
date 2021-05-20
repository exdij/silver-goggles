package kc.airport_task;

import kc.airport_task.entities.CargoEntity;
import kc.airport_task.entities.Flight;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class Endpoints {

    @PostMapping("/cargo")
    public void cargo(@RequestBody List<CargoEntity> cargos) {
        System.out.println(cargos);
        System.out.println("test");
    }
    @PostMapping("/flight")
    public void flight(@RequestBody List<Flight> flights){
        System.out.println(flights);
    }
}
