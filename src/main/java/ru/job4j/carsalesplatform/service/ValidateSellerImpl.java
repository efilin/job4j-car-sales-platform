package ru.job4j.carsalesplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.carsalesplatform.dao.SellerDao;
import ru.job4j.carsalesplatform.model.Seller;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ValidateSellerImpl implements ValidateSeller {

    @Autowired
    SellerDao sellerDao;

    @Override
    public void addSeller(Seller seller) {
        sellerDao.save(seller);
    }

    @Override
    public void updateSeller(Seller seller) {
        sellerDao.findById(seller.getId()).ifPresent(s -> sellerDao.save(s));
    }

    @Override
    public void deleteSeller(Seller seller) {
        sellerDao.delete(seller);
    }

    @Override
    public List<Seller> findAllSellers() {
        List<Seller> result = new ArrayList<>();
        sellerDao.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Seller findSellerById(int id) {
        return sellerDao.findById(id).orElse(null);
    }

    @Override
    public Seller findSellerByLogin(String username) {
        return sellerDao.findByUsername(username);
    }
}
