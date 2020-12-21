package com.space.service.impl;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class ShipSpecification  {
    public static Specification<Ship> filterByName(String name){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }
    public static Specification<Ship> filterByPlanet(String expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("planet"), "%"+expression+"%");
    }
    public static Specification<Ship> filterByMinSpeed(Double expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), expression);
    }
    public static Specification<Ship> filterByShipType(ShipType expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("shipType"), expression);
    }

    public static Specification<Ship> filterByAfter(Long expression) {

        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Date date = new Date(expression);
                return criteriaBuilder.greaterThan(root.get("prodDate"), date);
            }
        };
    }





    public static Specification<Ship> filterByBefore(Long expression){
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Date date = new Date(expression);
                return criteriaBuilder.lessThan(root.get("prodDate"), date);
            }
        };
    }

    public static Specification<Ship> filterByIsUsed(Boolean expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isUsed"), expression);
    }
    public static Specification<Ship> filterByMinCrewSize(Integer expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), expression);
    }
    public static Specification<Ship> filterByMaxCrewSize(Integer expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), expression);
    }

    public static Specification<Ship> filterByMinRating(Double expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), expression);
    }

    public static Specification<Ship> filterByMaxRating(Double expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("rating"), expression);
    }
    public static Specification<Ship> filterByMaxSpeed(Double expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("speed"), expression);
    }
    public static Specification<Ship> filterById(long expression){
        return (Specification<Ship>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), expression);
    }


}
