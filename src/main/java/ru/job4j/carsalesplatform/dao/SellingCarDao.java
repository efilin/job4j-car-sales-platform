package ru.job4j.carsalesplatform.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.carsalesplatform.model.SellingCar;

import java.sql.Timestamp;
import java.util.List;

public interface SellingCarDao extends CrudRepository<SellingCar, Integer> {

    List<SellingCar> findAllByOrderByIdAsc();

    List<SellingCar> findSellingCarsByCreatedAfter(Timestamp timestamp);

    List<SellingCar> findAllByManufacturer(String manufacturer);

    @Query("from SellingCar sc where sc.photo <>''")
    List<SellingCar> findCarsWithPhoto();

}
