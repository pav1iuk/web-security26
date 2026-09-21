package chnu.edu.websecurity26.model;

import lombok.*;

/*
  @author   Pavliuk
  @project  web-security26
  @class    Car
  @version  1.0.0
  @since    21.09.2026
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Car {
    private String id;
    private String brand;
    private String model;
    private int year;
}