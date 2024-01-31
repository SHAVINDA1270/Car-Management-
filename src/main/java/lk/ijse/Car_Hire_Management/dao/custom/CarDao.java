package lk.ijse.Car_Hire_Management.dao.custom;

import lk.ijse.Car_Hire_Management.dao.CrudDao;
import lk.ijse.Car_Hire_Management.entity.CarEntity;
import org.hibernate.Session;

public interface CarDao extends CrudDao<CarEntity> {
    boolean updateAvailabilityById(Integer carId, String newAvailability, Session session);

    Double getPerdayRentByCarId(Integer carId, Session session);

    Double getRefundableDepositByCarId(Integer carId, Session session);

    String getNameByCarId(Integer carID,Session session);
}
