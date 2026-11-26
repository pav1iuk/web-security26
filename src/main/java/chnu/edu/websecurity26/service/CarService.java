package chnu.edu.websecurity26.service;

import chnu.edu.websecurity26.model.Car;
import chnu.edu.websecurity26.repository.CarRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequiredArgsConstructor
@Log4j2
public class CarService {

    private final CarRepository carRepository;

    private final List<Car> cars = new ArrayList<>();

    {
        cars.add(new Car("1", "Audi", "RS6", 2023));
        cars.add(new Car("2", "BMW", "M5", 2024));
        cars.add(new Car("3", "Porsche", "911 GT3", 2023));
    }

    @PostConstruct
    void init() {
        carRepository.deleteAll();
        cars.forEach(this::createCar);
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
        Car existing = carRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setBrand(updatedCar.getBrand());
            existing.setModel(updatedCar.getModel());
            existing.setYear(updatedCar.getYear());
            return carRepository.save(existing);
        }
        return null;
    }

    public void deleteCar(String id) {
        String username;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            username = "unknown";
        } else {
            username = authentication.getName();
        }

        Car car = this.getCar(id);
        if (car != null) {
            log.info(car + " was deleted by " + username);
            carRepository.deleteById(id);
        }
    }
}