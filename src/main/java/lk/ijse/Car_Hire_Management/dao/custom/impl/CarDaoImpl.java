    package lk.ijse.Car_Hire_Management.dao.custom.impl;

    import lk.ijse.Car_Hire_Management.dao.custom.CarDao;
    import lk.ijse.Car_Hire_Management.entity.CarCategoryEntity;
    import lk.ijse.Car_Hire_Management.entity.CarEntity;
    import org.hibernate.HibernateException;
    import org.hibernate.Session;
    import org.hibernate.query.Query;

    import java.util.List;

    public class CarDaoImpl implements CarDao {
        @Override
        public Boolean save(CarEntity entity, Session session) {
            try {
                session.merge(entity);
                return true;
            } catch (Exception exception) {
                throw exception;
            }
        }


        @Override
        public Boolean update(CarEntity entity, Session session) {
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
                CarEntity carEntity = session.get(CarEntity.class,id);

                if(carEntity!= null){
                    session.remove(carEntity);
                    return true;
                }else{
                    return false;
                }
            }
            catch (Exception exception){
                throw exception;
            }
        }

        @Override
        public CarEntity get(Integer id, Session session) {
            try {
                String hql = "FROM CarEntity WHERE id = :id";
                Query<CarEntity> query = session.createQuery(hql, CarEntity.class);
                query.setParameter("id", id);

                List<CarEntity> resultList = query.getResultList();

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
        public List<CarEntity> getAll(Session session) {
            try {
                String hql = "FROM CarEntity";
                Query<CarEntity> query = session.createQuery(hql, CarEntity.class);
                List<CarEntity> carEntities = query.list();
                return carEntities;
            } catch (Exception e) {
                throw e;
            }
        }

        @Override
        public boolean updateAvailabilityById(Integer carId, String newAvailability, Session session) {
            try {
                String hql = "UPDATE CarEntity SET availability = :availability WHERE id = :carId";
                Query query = session.createQuery(hql);
                query.setParameter("availability", newAvailability);
                query.setParameter("carId", carId);

                int rowCount = query.executeUpdate();

                // Check if any rows were affected (rowCount > 0) to determine the success of the update
                return rowCount > 0;
            } catch (Exception exception) {
                throw exception;
            }


    }

        @Override
        public Double getPerdayRentByCarId(Integer carId, Session session) {
            try {
                String hql = "SELECT ce.Price_per_day FROM CarEntity ce WHERE ce.id = :id";
                Query<Double> query = session.createQuery(hql, Double.class);
                query.setParameter("id", carId);

                List<Double> resultList = query.getResultList();

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
        public Double getRefundableDepositByCarId(Integer carId, Session session) {
            try {
                String hql = "SELECT ce.refundable_Deposit FROM CarEntity ce WHERE ce.id = :id";
                Query<Double> query = session.createQuery(hql, Double.class);
                query.setParameter("id", carId);

                List<Double> resultList = query.getResultList();

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
        public String getNameByCarId(Integer carID, Session session) {
            try {
                String hql = "SELECT CONCAT(ce.brand, ' ', ce.model) FROM CarEntity ce WHERE ce.id = :id";
                Query<String> query = session.createQuery(hql, String.class);
                query.setParameter("id", carID);

                List<String> resultList = query.getResultList();

                if (resultList.isEmpty()) {
                    return null;
                } else {
                    return resultList.get(0);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
