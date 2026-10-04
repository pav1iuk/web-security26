package chnu.edu.websecurity26.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/*
  @author   Pavliuk
  @project  web-security26
  @class    Car
  @version  1.0.0
  @since    21.09.2026
*/
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@Document(collection = "cars")
public class Car extends Auditable {

    @Id
    private String id;
    private String brand;
    private String model;
    private int year;
}