package lk.ijse.Car_Hire_Management.service.custom.impl;

import lk.ijse.Car_Hire_Management.dao.DaoFactory;
import lk.ijse.Car_Hire_Management.dao.custom.CarDao;
import lk.ijse.Car_Hire_Management.dao.custom.CustomerDao;
import lk.ijse.Car_Hire_Management.dao.custom.RentDao;
import lk.ijse.Car_Hire_Management.db.SessionFactoryConfiguration;
import lk.ijse.Car_Hire_Management.dto.RentDto;
import lk.ijse.Car_Hire_Management.entity.CarEntity;
import lk.ijse.Car_Hire_Management.entity.CustomerEntity;
import lk.ijse.Car_Hire_Management.entity.RentEntity;
import lk.ijse.Car_Hire_Management.service.custom.RentService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class RentServiceImpl implements RentService {

    CustomerDao customerDao;

    RentDao rentDao;

    CarDao carDao;

    public RentServiceImpl() {
        customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CUSTOMER);
        rentDao = (RentDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.RENT);
        carDao = (CarDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CAR);


    }
    @Override
    public boolean saveRent(RentDto rentDto) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            CarEntity carEntity = carDao.get(rentDto.getCarId(), session);
            CustomerEntity customerEntity = customerDao.get(rentDto.getCustomerId(), session);

            RentEntity rentEntity = new RentEntity();
            rentEntity.setId(Integer.valueOf(rentDto.getId()));
            rentEntity.setFrom_date(rentDto.getFrom_date());
            rentEntity.setTo_date(rentDto.getTo_date());
            rentEntity.setPerDay_rent(rentDto.getPerDay_rent());
            rentEntity.setTotal(rentDto.getTotal());
            rentEntity.setAdvanced_payment(rentDto.getAdvanced_payment());
            rentEntity.setBalance(rentDto.getBalance());
            rentEntity.setIs_return(rentDto.getIs_return());
            rentEntity.setCarEntity(carEntity);
            rentEntity.setCustomerEntity(customerEntity);
            rentEntity.setRefundable_Deposit(rentDto.getRefundable_Deposit());

            // Set customer name and car details in RentDto
            rentEntity.setCustomer_name(rentDto.getCustomer_name());
            rentEntity.setCar(rentDto.getCar());

            System.out.println("Model + Brand Service " + rentEntity.getCar());
            System.out.println("Is Return Value(Service) =" + rentEntity.getIs_return());

            boolean save = rentDao.save(rentEntity, session);
            if (save) {
                System.out.println("******* Rent save method running****************");
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        }
    }

    @Override
    public boolean deleteRent(Integer id) {

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()){

            Transaction transaction = session.getTransaction();
            transaction.begin();
            Boolean delete = rentDao.delete(id, session);
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
    public boolean updateRent(RentDto rentDto) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            // Retrieve the existing RentEntity from the database
            RentEntity rentEntity = rentDao.get(Integer.valueOf(rentDto.getId()), session);

            // Check if rentEntity is not null before updating
            if (rentEntity != null) {
                CarEntity carEntity = carDao.get(rentDto.getCarId(), session);
                CustomerEntity customerEntity = customerDao.get(rentDto.getCustomerId(), session);

                // Update the properties of the existing RentEntity
                rentEntity.setFrom_date(rentDto.getFrom_date());
                rentEntity.setTo_date(rentDto.getTo_date());
                rentEntity.setPerDay_rent(rentDto.getPerDay_rent());
                rentEntity.setTotal(rentDto.getTotal());
                rentEntity.setAdvanced_payment(rentDto.getAdvanced_payment());
                rentEntity.setBalance(rentDto.getBalance());
                rentEntity.setIs_return(rentDto.getIs_return());
                rentEntity.setCarEntity(carEntity);
                rentEntity.setCustomerEntity(customerEntity);
                rentEntity.setRefundable_Deposit(rentDto.getRefundable_Deposit());
                rentDto.setCustomer_name(rentEntity.getCustomer_name());
                rentDto.setCar(rentEntity.getCar());

                System.out.println("Is Return Value (Service) =" + rentEntity.getIs_return());

                boolean save = rentDao.update(rentEntity, session);

                if (save) {
                    System.out.println("******* Rent update method running****************");
                    transaction.commit();
                    return true;
                } else {
                    transaction.rollback();
                    return false;
                }
            } else {
                transaction.rollback();
                return false; // Handle the case where RentEntity with the given ID is not found
            }
        }
    }


    @Override
    public RentEntity getRent(Integer id) {

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            Optional<RentEntity> optionalRentEntity = Optional.ofNullable(rentDao.get(id, session));
            return optionalRentEntity.orElse(null);

        } catch (Exception exception) {
            throw exception;
        }


    }

    @Override
    public List<RentDto> getAllRent() {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            try {
                List<RentEntity> rentEntities = rentDao.getAll(session);
                List<RentDto> rentDtoList = new ArrayList<>();

                for (RentEntity rentEntity : rentEntities) {
                    RentDto rentDto = new RentDto();
                    rentDto.setId(String.valueOf(rentEntity.getId()));
                    rentDto.setAdvanced_payment(rentEntity.getAdvanced_payment());
                    rentDto.setBalance(rentEntity.getBalance());
                    rentDto.setFrom_date(rentEntity.getFrom_date());
                    rentDto.setIs_return(rentEntity.getIs_return());
                    rentDto.setPerDay_rent(rentEntity.getPerDay_rent());
                    rentDto.setRefundable_Deposit(rentEntity.getRefundable_Deposit());
                    rentDto.setTo_date(rentEntity.getTo_date());
                    rentDto.setTotal(rentEntity.getTotal());
                    rentDto.setCustomer_name(rentEntity.getCustomer_name());
                    rentDto.setCar(rentEntity.getCar());

                    CustomerEntity customerEntity = rentEntity.getCustomerEntity();
                    if (customerEntity != null) {
                        rentDto.setCustomerId(customerEntity.getId());
                    }

                    CarEntity carEntity = rentEntity.getCarEntity();
                    if (carEntity != null) {
                        rentDto.setCarId(customerEntity.getId());
                    }


                    rentDtoList.add(rentDto);
                }

                transaction.commit();
                return rentDtoList;

            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    @Override
    public String getLastRentIsReturnByCustomerId(Integer customerId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            try {
                String lastRentIsReturn = rentDao.getLastRentIsReturnByCustomerId(customerId, session);

                transaction.commit();

                // Print messages based on lastRentIsReturn value
                if (lastRentIsReturn == null || "Yes".equals(lastRentIsReturn)) {
                    System.out.println("Can rent a Car");
                } else if ("No".equals(lastRentIsReturn)) {
                    System.out.println("This customer has already rented a car");
                }

                return lastRentIsReturn;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }

        }
    }

    @Override
    public boolean rentalDeurationLimit(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            System.out.println("Number of days: " + daysBetween);
            if(daysBetween > 0 && daysBetween <= 30){
                return true;
            }else{
                return false;
            }

            // Now you can use the 'daysBetween' variable as needed
            // For example, implement your business logic here

            // Return true or false based on your business logic

        } else {
            // Handle the case where either startDate or endDate is null
            System.out.println("Please provide both start and end dates.");
            return false;
        }
    }

    @Override
    public Double calculateTotal(Double perDayRent, LocalDate startDate, LocalDate endDate,Double refundableDeposit) {
        Double daysBetween = (double) ChronoUnit.DAYS.between(startDate, endDate);
        Double total = daysBetween*perDayRent+refundableDeposit;
        return total;
    }

    @Override
    public Double calculateBalance(Double total,Double AdvancedPayent) {
        Double balance = total - AdvancedPayent;
        return balance;
    }

    @Override
    public Double calculateExtraDates(Date Todate, LocalDate ReturnDate) {
        LocalDate localTodate = Todate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Double ExtraDays = (double) ChronoUnit.DAYS.between(localTodate, ReturnDate);
        if(ExtraDays>0) {
            return ExtraDays;
        }else{
            return null;
        }
    }


    @Override
    public Date getToDateById(Integer rentId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            try {
                Date toDate = rentDao.getToDateById(rentId, session);
                transaction.commit();

                // Print messages based on toDate value
                if (toDate == null) {
                    System.out.println("ToDate is null");
                }

                return toDate;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }

    }

    @Override
    public Double calculateChargesForExtraDates(Double extraDates, Integer rentId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            try {
                Double pricePerDay = rentDao.getCarPricePerDayByRentId(rentId, session);
                transaction.commit();
                System.out.println(pricePerDay);

                Double extraCharge = pricePerDay * 1.5 * extraDates;

                return extraCharge;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public Double calculateGrandTotal(Double ExtraCharges,Integer RentId) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            try {
                Double Balance = rentDao.getBalanceByRentId(RentId, session);
                transaction.commit();

                Double GrandTotal = Balance + ExtraCharges;

                return GrandTotal;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean updateIsReturnByRentId(Integer rentId, String isReturn) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();

            try {
                transaction.begin();

                // Retrieve the RentEntity by RentId
                RentEntity rentEntity = rentDao.get(rentId, session);

                if (rentEntity != null) {
                    // Update the is_return property
                    rentEntity.setIs_return(isReturn);

                    // Save the updated RentEntity
                    rentDao.update(rentEntity, session);

                    transaction.commit(); // Commit the transaction if the update is successful

                    return true; // Return true if the update is successful
                } else {
                    return false; // Return false if RentEntity with the given ID is not found
                }
            } catch (Exception e) {
                transaction.rollback(); // Rollback the transaction in case of an exception
                throw new RuntimeException("Error updating is_return for RentId: " + rentId, e);
            }
        }
    }

    @Override
    public List<RentDto> getAllOverdueRents() {
        List<RentDto> rentDtos = new ArrayList<>();

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            List<RentEntity> rentEntities = rentDao.getAllOverdueRents(session);

            for (RentEntity rentEntity : rentEntities) {
                RentDto rentDto = new RentDto();
                // Map RentEntity properties to RentDto properties
                rentDto.setId(String.valueOf(rentEntity.getId()));
                rentDto.setCarId(rentEntity.getCarEntity().getId());
                rentDto.setCustomerId(rentEntity.getCarEntity().getId());
                rentDto.setFrom_date(rentEntity.getFrom_date());
                rentDto.setTo_date(rentEntity.getTo_date());
                rentDto.setPerDay_rent(rentEntity.getPerDay_rent());
                rentDto.setAdvanced_payment(rentEntity.getAdvanced_payment());
                rentDto.setRefundable_Deposit(rentEntity.getRefundable_Deposit());
                rentDto.setBalance(rentEntity.getBalance());
                rentDto.setTotal(rentEntity.getTotal());
                rentDto.setIs_return(rentEntity.getIs_return());
                rentDto.setCustomer_name(rentEntity.getCustomer_name());
                rentDto.setCar(rentEntity.getCar());

                rentDtos.add(rentDto);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rentDtos;
    }



}
