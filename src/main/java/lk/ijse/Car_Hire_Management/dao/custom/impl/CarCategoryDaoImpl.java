package lk.ijse.Car_Hire_Management.dao.custom.impl;

import lk.ijse.Car_Hire_Management.dao.custom.CarCategoryDao;
import lk.ijse.Car_Hire_Management.entity.CarCategoryEntity;
import lk.ijse.Car_Hire_Management.entity.CustomerEntity;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CarCategoryDaoImpl implements CarCategoryDao {
    @Override
    public Boolean save(CarCategoryEntity carCategoryEntity, Session session) {
        session.save(carCategoryEntity);
        return true;
    }

    @Override
    public Boolean update(CarCategoryEntity entity, Session session) {
        try{
            session.update(entity);
            return true;
        }
        catch (Exception exception){
            throw exception;
        }
    }

    @Override
    public Boolean delete(Integer id, Session session) {

        try{
            CarCategoryEntity carCategoryEntity = session.get(CarCategoryEntity.class,id);

            if(carCategoryEntity!= null){
                session.remove(carCategoryEntity);
                return true;
            }else{
                return false;
            }
        }
        catch (Exception exception){
            throw exception;
        }
    }


    public CarCategoryEntity get(Integer id, Session session) {
        try {
            String hql = "FROM CarCategoryEntity WHERE id = :id";
            Query<CarCategoryEntity> query = session.createQuery(hql, CarCategoryEntity.class);
            query.setParameter("id", id);

            List<CarCategoryEntity> resultList = query.getResultList();

            if (resultList.isEmpty()) {
                return null;
            } else {
                return resultList.get(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public List<CarCategoryEntity> getAll(Session session) {

        try {
            String hql = "FROM  CarCategoryEntity";
            Query<CarCategoryEntity> query = session.createQuery(hql , CarCategoryEntity.class);
            List<CarCategoryEntity> carCategoryEntityList = query.list();
            return carCategoryEntityList;


        } catch (Exception e) {
            throw e;


        }
    }


    @Override
    public CarCategoryEntity getCarCategoryByName(String categoryName, Session session) {
        Query<CarCategoryEntity> query = session.createQuery("FROM CarCategoryEntity c WHERE c.name = :categoryName", CarCategoryEntity.class);
        query.setParameter("categoryName", categoryName);

        try {
            return query.uniqueResult();
        } catch (NonUniqueResultException e) {
            // Handle non-unique result (e.g., log or throw a custom exception)
            e.printStackTrace();
            throw new RuntimeException("Non-unique result for category name: " + categoryName, e);
        } catch (HibernateException e) {
            // Handle other Hibernate exceptions (e.g., log or throw a custom exception)
            e.printStackTrace();
            throw new RuntimeException("Error retrieving category by name: " + categoryName, e);
        }
    }
}
