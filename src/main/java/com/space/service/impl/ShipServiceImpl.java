package com.space.service.impl;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {
    @Autowired
    private ShipRepository shipRepository;

    @Override
    public List<Ship> getAll() {
        return shipRepository.findAll();
    }
    public Ship createShip(Ship ship){
        return shipRepository.saveAndFlush(ship);
    }
    public long count() {
        return shipRepository.count();
    }
    public void deleteShip(long id){
        shipRepository.deleteById(id);
    }

    public Ship updateShip(Ship ship){
        return shipRepository.saveAndFlush(ship);
    }
    public Optional<Ship> findOne(Specification<Ship> spec){
        return shipRepository.findOne(spec);
    }
    public List<Ship> getAll(Specification<Ship> spec) {
        return shipRepository.findAll(spec);
    }

    public List<Ship> getById(Specification<Ship> spec){
        return shipRepository.findAll(spec);
    }

    public List<Ship> getAll(Specification<Ship> spec, Sort id) {
        return shipRepository.findAll(spec, id);
    }


//TODO: рассмотреть возможность фильтрации через спецификации (архитектура проекта предполагает именно такой вариант,
// но согл. источникам в интернете это типонебезопасный вариант, плюс пока что не найти адекватной документашки
// по мержу опциональных фильтров)
}
