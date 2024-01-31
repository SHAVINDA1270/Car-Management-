package lk.ijse.Car_Hire_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentDto {
    private String id;
    private Integer CarId;
    private Integer CustomerId;
    private Date from_date;
    private Date to_date;
    private Double perDay_rent;
    private Double advanced_payment;
    private Double refundable_Deposit;
    private Double balance;
    private Double total;
    private String is_return;
    private String Customer_name;
    private String Car;
}
