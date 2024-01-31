package lk.ijse.Car_Hire_Management.dao.custom;

import lk.ijse.Car_Hire_Management.dao.CrudDao;
import lk.ijse.Car_Hire_Management.dto.CustomerDto;
import lk.ijse.Car_Hire_Management.entity.CustomerEntity;
import org.hibernate.Session;

public interface CustomerDao extends CrudDao<CustomerEntity> {
    String getCustomerById(Integer customerId, Session session);
}
