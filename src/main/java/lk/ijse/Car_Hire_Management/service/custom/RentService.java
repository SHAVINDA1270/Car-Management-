package lk.ijse.Car_Hire_Management.service.custom;

import lk.ijse.Car_Hire_Management.dto.RentDto;
import lk.ijse.Car_Hire_Management.entity.RentEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RentService extends SuperService{
    boolean saveRent(RentDto rentDto);
    boolean deleteRent(Integer id);

    boolean updateRent(RentDto rentDto);

    RentEntity getRent(Integer id);

    List<RentDto> getAllRent();

    String getLastRentIsReturnByCustomerId(Integer customerId);

    boolean rentalDeurationLimit(LocalDate startDate, LocalDate endDate);

    Double calculateTotal(Double perDayRent,LocalDate startDate, LocalDate endDate,Double refundableDeposit);

    Double calculateBalance(Double total,Double AdvancedPayent);

    Double calculateExtraDates(Date ToDate,LocalDate ReturnDate);

    Date getToDateById(Integer rentId);

    Double calculateChargesForExtraDates(Double ExtraDates,Integer RentId);

    Double calculateGrandTotal(Double ExtraCharges,Integer RentId);

    boolean updateIsReturnByRentId(Integer rentId, String isReturn);

    List<RentDto> getAllOverdueRents();
}
