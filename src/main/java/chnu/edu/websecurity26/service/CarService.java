package chnu.edu.websecurity26.service;

import chnu.edu.websecurity26.model.Car;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
  @author   Pavliuk
  @project  web-security26
  @class    CarService
  @version  1.0.0
  @since    21.09.2026
*/
@Service
public class CarService {
    private final List<Car> cars = new ArrayList<>();

    @PostConstruct
    private void init() {
        cars.add(new Car("1", "Audi", "RS6", 2023));
        cars.add(new Car("2", "BMW", "M5", 2024));
        cars.add(new Car("3", "Porsche", "911 GT3", 2023));
    }

    public List<Car> getAllCars() {
        return cars;
    }

    public Car getCar(String id) {
        return cars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Car createCar(Car car) {
        cars.add(car);
        return car;
    }

    public Car updateCar(String id, Car updatedCar) {
        Car existingCar = getCar(id);
        if (existingCar != null) {
            existingCar.setBrand(updatedCar.getBrand());
            existingCar.setModel(updatedCar.getModel());
            existingCar.setYear(updatedCar.getYear());
            return existingCar;
        }
        return null;
    }

    public boolean deleteCar(String id) {
        Car car = getCar(id);
        if (car != null) {
            cars.remove(car);
            return true;
        }
        return false;
    }
}