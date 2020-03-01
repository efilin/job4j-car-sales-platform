package ru.job4j.carsalesplatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "s_car")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String manufacturer;
    private String model;
    private int mileage;
    private int price;
    @Column(name = "production_year")
    private int productionYear;
    @Column(name = "car_body")
    private String carBody;
    private String engine;
    private String transmission;
    private String description;
    private String photo;
    @ManyToOne()
    private Seller seller;
    @Column(name = "on_sale")
    private boolean onSale;
    private Timestamp created;
}
