package lk.ijse.Car_Hire_Management.service.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.Car_Hire_Management.dao.DaoFactory;
import lk.ijse.Car_Hire_Management.dao.custom.CarCategoryDao;
import lk.ijse.Car_Hire_Management.dao.custom.CarDao;
import lk.ijse.Car_Hire_Management.dao.custom.RentDao;
import lk.ijse.Car_Hire_Management.db.SessionFactoryConfiguration;
import lk.ijse.Car_Hire_Management.dto.CarCategoryDto;
import lk.ijse.Car_Hire_Management.dto.CarDto;
import lk.ijse.Car_Hire_Management.entity.CarCategoryEntity;
import lk.ijse.Car_Hire_Management.entity.CarEntity;
import lk.ijse.Car_Hire_Management.service.custom.CarService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {

    private CarDao carDao;
    private CarCategoryDao carCategoryDao;

    private RentDao rentDao;

    public CarServiceImpl() throws Exception {
        carDao = (CarDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR);
        carCategoryDao = (CarCategoryDao) DaoFactory.getDao(DaoFactory.DaoTypes.CAR_CATEGORY);
        rentDao = (RentDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.RENT);
    }

    @Override
    public boolean saveCar(CarDto carDto) {
        System.out.println("CarServiceImpl saveCar Method is running");
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            CarCategoryEntity carCategoryEntity = carCategoryDao.get(carDto.getCarCategoryId(),session);



            CarEntity carEntity = new CarEntity();

            carEntity.setId(carDto.getId());
            carEntity.setBrand(carDto.getBrand());
            carEntity.setModel(carDto.getModel());
            carEntity.setYear(carDto.getYear());
            carEntity.setVehicle_category(carDto.getVehicle_category());
            carEntity.setCar_Number(carDto.getCar_number());
            carEntity.setPrice_per_day(carDto.getPrice_per_day());
            carEntity.setAvailability(carDto.getAvailability());
            carEntity.setCarCategoryEntity(carCategoryEntity);
            carEntity.setRefundable_Deposit(carDto.getRefundable_Deposit());



            Boolean save = carDao.save(carEntity, session);
            if (save) {
                System.out.println("******************************save method running****************");
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        }
    }

    @Override
    public boolean deleteCar(Integer id) {

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()){

            Transaction transaction = session.getTransaction();
            transaction.begin();
            Boolean delete = carDao.delete(id, session);
            if (delete) {
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        }
        catch (Exception exception){
            throw exception;
        }

    }

    @Override
    public boolean updateCar(CarDto carDto) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            CarCategoryEntity carCategoryEntity = carCategoryDao.get(carDto.getCarCategoryId(),session);



            CarEntity carEntity = new CarEntity();
            carEntity.setId(carDto.getId());
            carEntity.setBrand(carDto.getBrand());
            carEntity.setModel(carDto.getModel());
            carEntity.setYear(carDto.getYear());
            carEntity.setVehicle_category(carDto.getVehicle_category());
            carEntity.setCar_Number(carDto.getCar_number());
            carEntity.setPrice_per_day(carDto.getPrice_per_day());
            carEntity.setAvailability(carDto.getAvailability());
            carEntity.setRefundable_Deposit(carDto.getRefundable_Deposit());
            carEntity.setCarCategoryEntity(carCategoryEntity);


            Boolean save = carDao.update(carEntity, session);
            if (save) {
                System.out.println("******************************Update method running****************");
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        }
    }

    @Override
    public CarDto getCar(String id) {
        // Implement the getCar method as needed
        return null;
    }

    @Override
    public List<CarDto> getAllCar() {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            try {
                List<CarEntity> carEntities = carDao.getAll(session);
                List<CarDto> carDtoList = new ArrayList<>();

                for (CarEntity carEntity : carEntities) {
                    CarDto carDto = new CarDto();
                    carDto.setId(carEntity.getId());
                    carDto.setBrand(carEntity.getBrand());
                    carDto.setModel(carEntity.getModel());
                    carDto.setYear(carEntity.getYear());
                    carDto.setVehicle_category(carEntity.getVehicle_category());
                    carDto.setCar_number(carEntity.getCar_Number());
                    carDto.setPrice_per_day(carEntity.getPrice_per_day());
                    carDto.setAvailability(carEntity.getAvailability());
                    carDto.setRefundable_Deposit(carEntity.getRefundable_Deposit());

                    CarCategoryEntity carCategoryEntity = carEntity.getCarCategoryEntity();
                    if (carCategoryEntity != null) {
                        carDto.setCarCategoryId(carCategoryEntity.getId());
                    }

                    carDtoList.add(carDto);
                }

                transaction.commit();
                return carDtoList;

            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    @Override
    public boolean updateAvailabilityById(Integer carId, String newAvailability) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            CarEntity carEntity = new CarEntity();
            carEntity.setAvailability(newAvailability);

            Boolean save = carDao.updateAvailabilityById(carId,newAvailability, session);
            if (save) {
                System.out.println("***********update method running**********");
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        }
    }

    @Override
    public Double getPerdayRentByCarId(Integer carId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            return carDao.getPerdayRentByCarId(carId, session);
        } catch (Exception e) {
            throw new RuntimeException("Error getting perday rent for carId: " + carId, e);
        }
    }

    @Override
    public Double getRefundableDepositByCarId(Integer carId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            return carDao.getRefundableDepositByCarId(carId, session);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Refundable Deposit for carId: " + carId, e);
        }
    }

    @Override
    public boolean updateAvailabilityByRentId(Integer RentId, String newAvailability) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Integer CarId = rentDao.getCarIdByRentId(RentId,session);
            boolean Update = updateAvailabilityById(CarId,newAvailability);
            if(Update){
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException("Error Updating Availability: " + RentId, e);
        }
    }
}
