    package lk.ijse.Car_Hire_Management.entity;

    import lk.ijse.Car_Hire_Management.entity.CarCategoryEntity;
    import lk.ijse.Car_Hire_Management.entity.RentEntity;
    import lk.ijse.Car_Hire_Management.entity.SuperEntity;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import javax.persistence.*;
    import java.util.List;

    @Entity
    @Table(name = "Car")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CarEntity implements SuperEntity {

        @Id
        private Integer id;

        private String brand;
        private String model;
        private String year;
        private String vehicle_category;
        private Double Price_per_day;
        private String availability;
        private Double refundable_Deposit;
        private String car_Number;

        @ManyToOne
        @JoinColumn(name = "Car_category_id")
        private CarCategoryEntity carCategoryEntity;

        @OneToMany(mappedBy = "carEntity", targetEntity = RentEntity.class)
        List<RentEntity> rentEntities;
    }
