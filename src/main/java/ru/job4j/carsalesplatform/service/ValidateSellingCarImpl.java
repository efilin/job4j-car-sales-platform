package ru.job4j.carsalesplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.carsalesplatform.dao.SellingCarDao;
import ru.job4j.carsalesplatform.model.SellingCar;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ValidateSellingCarImpl implements ValidateSellingCar {

    @Autowired
    SellingCarDao sellingCarDao;

    @Override
    public void addCar(SellingCar car) {
        sellingCarDao.save(car);
    }

    @Override
    public void updateCar(SellingCar car) {
        sellingCarDao.findById(car.getId()).ifPresent(s -> sellingCarDao.save(s));
    }

    @Override
    public void deleteCar(SellingCar car) {
        sellingCarDao.delete(car);
    }

    @Override
    public List<SellingCar> findAllCars() {
        return sellingCarDao.findAllByOrderByIdAsc();
    }

    @Override
    public List<SellingCar> findCarsWithPhoto() {
        return sellingCarDao.findCarsWithPhoto();
    }

    @Override
    public List<SellingCar> findLastDayCars() {
        LocalDate date = LocalDateTime.now().toLocalDate();
        Timestamp currentDateTime = Timestamp.valueOf(date.atStartOfDay());
        return sellingCarDao.findSellingCarsByCreatedAfter(currentDateTime);
    }

    @Override
    public List<SellingCar> findCurrentManufacturerCars(String manufacturer) {
        return sellingCarDao.findAllByManufacturer(manufacturer);
    }

    @Override
    public SellingCar findCarById(int id) {
        return sellingCarDao.findById(id).orElse(null);
    }

    @Override
    public void changeSaleStatus(int id) {
        sellingCarDao.findById(id).orElse(null)
                .setOnSale(!sellingCarDao.findById(id).orElse(null).isOnSale());
    }
}
