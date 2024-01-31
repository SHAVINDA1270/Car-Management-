package lk.ijse.Car_Hire_Management.service.custom.impl;

import lk.ijse.Car_Hire_Management.dao.DaoFactory;
import lk.ijse.Car_Hire_Management.dao.custom.UserDao;
import lk.ijse.Car_Hire_Management.db.SessionFactoryConfiguration;
import lk.ijse.Car_Hire_Management.dto.UserDto;
import lk.ijse.Car_Hire_Management.entity.UserEntity;
import lk.ijse.Car_Hire_Management.service.custom.UserService;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl() {
        userDao = (UserDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.USER);
    }

    @Override
    public boolean saveUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setMobile(userDto.getMobile());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());

        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Boolean save = userDao.save(userEntity, session);
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
    public UserDto getUser(String userName) {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            UserEntity userEntity = userDao.getByUsername(userName, session);

            transaction.commit();

            if (userEntity != null) {
                return new UserDto(
                        userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getEmail(),
                        userEntity.getMobile(),
                        userEntity.getUsername(),
                        userEntity.getPassword()
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            throw new RuntimeException("Error occurred while fetching user", e);
        }
    }

}
