package chnu.edu.websecurity26.service;

import chnu.edu.websecurity26.model.Car;
import chnu.edu.websecurity26.repository.CarRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
  @author   Pavliuk
  @project  web-security26
  @class    CarService
  @version  1.0.0
  @since    21.09.2026
*/
@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    @PostConstruct
    void init() {
        carRepository.deleteAll();
        List<Car> initialCars = Arrays.asList(
                new Car("1", "Audi", "RS6", 2023),
                new Car("2", "BMW", "M5", 2024),
                new Car("3", "Porsche", "911 GT3", 2023)
        );
        carRepository.saveAll(initialCars);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCar(String id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(String id, Car updatedCar) {
        updatedCar.setId(id);
        return carRepository.save(updatedCar);
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
    }
}