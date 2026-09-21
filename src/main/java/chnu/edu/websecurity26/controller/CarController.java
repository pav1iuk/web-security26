package chnu.edu.websecurity26.controller;

import chnu.edu.websecurity26.model.Car;
import chnu.edu.websecurity26.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
  @author   Pavliuk
  @project  web-security26
  @class    CarController
  @version  1.0.0
  @since    21.09.2026
*/
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public List<Car> getAll() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car getCar(@PathVariable String id) {
        return carService.getCar(id);
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.createCar(car);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable String id, @RequestBody Car car) {
        return carService.updateCar(id, car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
    }
}