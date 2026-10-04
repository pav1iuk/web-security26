package chnu.edu.websecurity26.repository;

import chnu.edu.websecurity26.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

/*
  @author   Pavliuk
  @project   web-security26
  @class  CarRepository
  @version  1.0.0 
  @since 04.10.2026 - 22.29
*/
public interface CarRepository extends MongoRepository<Car, String> {
}
