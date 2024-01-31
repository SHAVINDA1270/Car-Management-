package lk.ijse.Car_Hire_Management.dao.custom;

import lk.ijse.Car_Hire_Management.dao.CrudDao;
import lk.ijse.Car_Hire_Management.entity.RentEntity;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RentDao extends CrudDao<RentEntity> {
    String getLastRentIsReturnByCustomerId(Integer customerId, Session session);

    Date getToDateById(Integer rentId, Session session);

    Double getCarPricePerDayByRentId(Integer RentId, Session session);

    Double getBalanceByRentId(Integer RentId, Session session);

    Integer getCarIdByRentId(Integer RentId, Session session);

    boolean UpdateIsReturnByRentId(Integer RentId,String Isreturn ,Session session);

    List<RentEntity> getAllOverdueRents(Session session);


}
