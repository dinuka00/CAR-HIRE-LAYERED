package lk.ijse.carHire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CarDto {

    private String carId;
    private String licensePlate;
    private String make;
    private String model;
    private Integer year;
    private String categoryId;
    private Double dailyRentalRate;
    private String available;


}
