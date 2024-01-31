package lk.ijse.Car_Hire_Management.service.custom.impl;

import lk.ijse.Car_Hire_Management.dao.DaoFactory;
import lk.ijse.Car_Hire_Management.dao.custom.CustomerDao;
import lk.ijse.Car_Hire_Management.db.SessionFactoryConfiguration;
import lk.ijse.Car_Hire_Management.dto.CustomerDto;
import lk.ijse.Car_Hire_Management.entity.CarCategoryEntity;
import lk.ijse.Car_Hire_Management.entity.CustomerEntity;
import lk.ijse.Car_Hire_Management.service.custom.CustomerService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao;
    public CustomerServiceImpl() {
        customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CUSTOMER);
    }

    @Override
    public boolean saveCus(CustomerDto customerdto) {
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setId(Integer.valueOf(customerdto.getId()));
        customerEntity.setName(customerdto.getName());
        customerEntity.setNic(customerdto.getNic());
        customerEntity.setAddress(customerdto.getAddress());
        customerEntity.setMobile(customerdto.getMobile());

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Boolean save = customerDao.save(customerEntity,session);
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
    public boolean deleteCus(Integer id) {


        try (Session session = SessionFactoryConfiguration.getInstance().getSession()){

            Transaction transaction = session.getTransaction();
            transaction.begin();
            Boolean delete = customerDao.delete(id, session);
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
    public boolean updateCus(CustomerDto customerDto) {

        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setId(Integer.valueOf(customerDto.getId()));
        customerEntity.setName(customerDto.getName());
        customerEntity.setNic(customerDto.getNic());
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setMobile(customerDto.getMobile());

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            Boolean update = customerDao.update(customerEntity,session);
            if (update) {
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        }

    }

    @Override
    public CustomerDto getCus(Integer id) {
        return null;

    }

    @Override
    public List<CustomerDto> getAllCus() {

        try{Session session = SessionFactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();

            List<CustomerEntity> customerEntities = customerDao.getAll(session);

            List<CustomerDto> customerDtos = new ArrayList<>();



            for (CustomerEntity customerEntity : customerEntities) {
                customerDtos.add(new CustomerDto(customerEntity.getId(),
                        customerEntity.getName(),
                        customerEntity.getNic(),
                        customerEntity.getAddress(),
                        customerEntity.getMobile()));


            }
            transaction.commit();
            return customerDtos;

        }

        catch (Exception exception){
            throw exception;
        }



    }

    @Override
    public CustomerEntity getCustomerById(Integer customerId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            try {

                CustomerEntity customerEntity = session.get(CustomerEntity.class, customerId);

                transaction.commit();
                return customerEntity;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
