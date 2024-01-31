package lk.ijse.Car_Hire_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentTableModelDto {
    private String id;
    private String Car;
    private String Customer_name;
    private Double balance;
    private Date from_date;
    private Date to_date;
    private String is_return;
}
