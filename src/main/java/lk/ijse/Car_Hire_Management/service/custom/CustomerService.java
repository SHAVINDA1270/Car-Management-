package lk.ijse.Car_Hire_Management.service.custom;

import lk.ijse.Car_Hire_Management.dto.CustomerDto;
import lk.ijse.Car_Hire_Management.entity.CustomerEntity;

import java.util.List;

public interface CustomerService extends SuperService{
    boolean saveCus(CustomerDto customerDto);
    boolean deleteCus(Integer id);

    boolean updateCus(CustomerDto customerDto);

    CustomerDto getCus(Integer id);
    List<CustomerDto> getAllCus();

    CustomerEntity getCustomerById(Integer customerId);
}
