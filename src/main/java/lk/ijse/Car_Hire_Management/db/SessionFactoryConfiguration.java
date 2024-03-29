package lk.ijse.Car_Hire_Management.db;


import lk.ijse.Car_Hire_Management.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfiguration {
    private static SessionFactoryConfiguration sessionFactoryConfiguration;

    private SessionFactory sessionFactory;

    private SessionFactoryConfiguration() {
        Configuration configuration = new Configuration().configure()
                .addAnnotatedClass(CarCategoryEntity.class)
                .addAnnotatedClass(CarEntity.class)
                .addAnnotatedClass(CustomerEntity.class)
                .addAnnotatedClass(RentEntity.class)
                .addAnnotatedClass(UserEntity.class);


        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactoryConfiguration getInstance() {
        return sessionFactoryConfiguration == null ? sessionFactoryConfiguration = new SessionFactoryConfiguration()
                : sessionFactoryConfiguration;

    }

    public Session getSession(){
        return sessionFactory.openSession();
    }

}
