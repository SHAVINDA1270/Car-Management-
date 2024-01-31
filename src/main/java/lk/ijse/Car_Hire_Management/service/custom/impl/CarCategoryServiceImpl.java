package lk.ijse.Car_Hire_Management.service.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.Car_Hire_Management.dao.DaoFactory;
import lk.ijse.Car_Hire_Management.dao.custom.CarCategoryDao;
import lk.ijse.Car_Hire_Management.dao.custom.CustomerDao;
import lk.ijse.Car_Hire_Management.db.SessionFactoryConfiguration;
import lk.ijse.Car_Hire_Management.dto.CarCategoryDto;
import lk.ijse.Car_Hire_Management.dto.CustomerDto;
import lk.ijse.Car_Hire_Management.dto.UserDto;
import lk.ijse.Car_Hire_Management.entity.CarCategoryEntity;
import lk.ijse.Car_Hire_Management.entity.CustomerEntity;
import lk.ijse.Car_Hire_Management.entity.UserEntity;
import lk.ijse.Car_Hire_Management.service.custom.CarCategoryService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CarCategoryServiceImpl implements CarCategoryService {

    CarCategoryDao carCategoryDao;

    public CarCategoryServiceImpl() {
        carCategoryDao = (CarCategoryDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR_CATEGORY);
    }
    @Override
    public boolean saveCategory(CarCategoryDto carCategoryDto) {
        CarCategoryEntity carCategoryEntity = new CarCategoryEntity();

        carCategoryEntity.setId(carCategoryDto.getId());
        carCategoryEntity.setName(carCategoryDto.getName());


        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Boolean save = carCategoryDao.save(carCategoryEntity,session);
            if (save) {
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        }
    }

    @Override
    public boolean deleteCategory(CarCategoryDto id) {
        return false;
    }

    @Override
    public boolean UpdateCAtegory(CarCategoryDto carCategoryDto) {
        CarCategoryEntity carCategoryEntity = new CarCategoryEntity();

        carCategoryEntity.setId(carCategoryDto.getId());
        carCategoryEntity.setName(carCategoryDto.getName());


        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Boolean save = carCategoryDao.update(carCategoryEntity,session);
            if (save) {
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        }
    }

    @Override
    public CarCategoryDto getCategory(String name) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            CarCategoryEntity existingCategory = carCategoryDao.getCarCategoryByName(name, session);

            // Handle the case where the category does not exist
            if (existingCategory == null) {
                return null;
            }

            // Show a warning alert if the category already exists
            new Alert(Alert.AlertType.WARNING, "Car Category already exists!").show();

            // Convert the entity to DTO and return
            return new CarCategoryDto(existingCategory.getId(), existingCategory.getName());
        }
    }





    @Override
    public List<CarCategoryDto> getAllCategory() {
        try{Session session = SessionFactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();

            List<CarCategoryEntity> carCategoryEntities = carCategoryDao.getAll(session);

            List<CarCategoryDto> carCategoryDtos = new ArrayList<>();



            for (CarCategoryEntity carCategoryEntity : carCategoryEntities) {
                carCategoryDtos.add(new CarCategoryDto(carCategoryEntity.getId(),
                        carCategoryEntity.getName()));


            }
            transaction.commit();
            return carCategoryDtos;

        }

        catch (Exception exception){
            throw exception;
        }
    }
    @Override
    public CarCategoryDto getCarCategoryByName(String categoryName) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            CarCategoryDao carCategoryDao = (CarCategoryDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR_CATEGORY);
            CarCategoryEntity carCategoryEntity = carCategoryDao.getCarCategoryByName(categoryName, session);

            transaction.commit();

            if (carCategoryEntity != null) {
                return new CarCategoryDto(carCategoryEntity.getId(), carCategoryEntity.getName());
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log or throw a custom exception)
            e.printStackTrace();
            return null;
        }
    }
}
