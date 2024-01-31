package lk.ijse.Car_Hire_Management.dao.custom.impl;

import lk.ijse.Car_Hire_Management.dao.custom.UserDao;
import lk.ijse.Car_Hire_Management.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public Boolean save(UserEntity userEntity, Session session) {
        session.save(userEntity);
        return true;
    }

    @Override
    public Boolean update(UserEntity entity, Session session) {
        // Your update logic goes here
        return null;
    }

    @Override
    public Boolean delete(Integer id, Session session) {
        // Your delete logic goes here
        return null;
    }

    @Override
    public UserEntity get(Integer id, Session session) {


        try {
            Query<UserEntity> query = session.createQuery("FROM UserEntity WHERE id = :id", UserEntity.class);
            query.setParameter("id", id);

            UserEntity userEntity = query.uniqueResult();

            return userEntity;
        } catch (Exception e) {

            throw e;
        }
    }

    @Override
    public UserEntity getByUsername(String username, Session session) {
        try {
            Query<UserEntity> query = session.createQuery("FROM UserEntity WHERE username = :username", UserEntity.class);
            query.setParameter("username", username);

            return query.uniqueResult();
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            throw new RuntimeException("Error occurred while fetching user by username", e);
        }
    }

    @Override
    public List<UserEntity> getAll(Session session) {

        return null;
    }


}
