package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.impl.ShipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.space.service.impl.ShipSpecification.*;


@RestController
public class ShipController {
    @Autowired
    private ShipServiceImpl shipService;

    @RequestMapping("/rest/ships")
    public List<Ship> ships(@RequestParam Map<String, String> allRequestParams) {


        int pageSize = 3;
        int pageNumber = 0;

        if (allRequestParams.containsKey("pageSize")) {
            pageSize = Integer.parseInt(allRequestParams.get("pageSize"));
            allRequestParams.remove("pageSize");
        }
        if (allRequestParams.containsKey("pageNumber")) {
            pageNumber = Integer.parseInt(allRequestParams.get("pageNumber"));
            allRequestParams.remove("pageNumber");
        }
        List<Ship> test = getShipsFiltered(allRequestParams);
        int shipsNumber = test.size();
        int startIndex = pageSize * pageNumber;
        List<Ship> result = new ArrayList<>();
        for (int i = startIndex; i < shipsNumber && i < pageSize + startIndex; i++) {
            result.add(test.get(i));
        }

        return result;
    }

    @RequestMapping("/rest/ships/count")
    public long count(@RequestParam Map<String, String> allRequestParams) {
        List<Ship> test = getShipsFiltered(allRequestParams);
        return test.size();
    }


    @RequestMapping(value = "/rest/ships/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Ship> getShipById(@PathVariable String id) {
        if (isIdValid(id)) {
            Specification<Ship> spec = Specification
                    .where(filterById(Long.parseLong(id)));
            try {
                Ship ship = shipService.getById(spec).get(0);
                return new ResponseEntity<>(ship, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

    public boolean isIdValid(String id) {
        long number;
        try {
            number = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return false;
        }
        return number > 0 && !id.contains(".");
    }

    @RequestMapping(value = "/rest/ships", method = RequestMethod.POST)
    public ResponseEntity<Ship> createShip(@RequestBody Map<String, String> allRequestParams) {
        Ship ship = new Ship();
        if (paramsForNewShipsExist(allRequestParams)) {
            if (addShipParams(allRequestParams, ship) != null) {

                return new ResponseEntity<>(shipService.createShip(ship), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/rest/ships/{id}",
            method = RequestMethod.POST)
    public ResponseEntity<Ship> updateShip(@PathVariable String id, @RequestBody Map<String, String> allRequestParams) {
        if (isIdValid(id)) {
            Specification<Ship> spec = Specification
                    .where(filterById(Integer.parseInt(id)));
            Ship ship;
            try {
                ship = shipService.findOne(spec).get();
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (addShipParams(allRequestParams, ship) == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }
            shipService.updateShip(ship);
            return new ResponseEntity<>(ship, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

    @RequestMapping(value = "/rest/ships/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Ship> deleteShip(@PathVariable String id) {

        if (isIdValid(id)) {
            try {
                shipService.deleteShip(Long.parseLong(id));
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public List<Ship> getShipsFiltered(Map<String, String> filters) {

        Specification<Ship> spec = Specification
                .where(filters.get("name") == null ? null : filterByName(filters.get("name")))
                .and(filters.get("planet") == null ? null : filterByPlanet(filters.get("planet")))
                .and(filters.get("minSpeed") == null ? null : filterByMinSpeed(Double.parseDouble(filters.get("minSpeed"))))
                .and(filters.get("maxSpeed") == null ? null : filterByMaxSpeed(Double.parseDouble(filters.get("maxSpeed"))))
                .and(filters.get("shipType") == null ? null : filterByShipType(ShipType.valueOf(filters.get("shipType"))))
                .and(filters.get("after") == null ? null : filterByAfter(Long.parseLong(filters.get("after"))))
                .and(filters.get("before") == null ? null : filterByBefore(Long.parseLong(filters.get("before"))))
                .and(filters.get("isUsed") == null ? null : filterByIsUsed(Boolean.parseBoolean(filters.get("isUsed"))))
                .and(filters.get("minCrewSize") == null ? null : filterByMinCrewSize(Integer.parseInt(filters.get("minCrewSize"))))
                .and(filters.get("maxCrewSize") == null ? null : filterByMaxCrewSize(Integer.parseInt(filters.get("maxCrewSize"))))
                .and(filters.get("minRating") == null ? null : filterByMinRating(Double.parseDouble(filters.get("minRating"))))
                .and(filters.get("maxRating") == null ? null : filterByMaxRating(Double.parseDouble(filters.get("maxRating"))));
        List<Ship> result;
        if (filters.containsKey("order"))
            result = shipService.getAll(spec, Sort.by(filters.get("order").toLowerCase()));
            //TODO: исправить на енамовский объект))
        else
            result = shipService.getAll(spec);

        return result;

    }

    public boolean paramsForNewShipsExist(Map<String, String> params) {
        String[] requiredParams = {"name", "planet", "shipType", "prodDate", "speed", "crewSize"};

        for (String requiredParam : requiredParams) {
            if (!params.containsKey(requiredParam))
                return false;
        }

        return true;
    }

    public Ship addShipParams(Map<String, String> allRequestParams, Ship ship) {
        if (allRequestParams.containsKey("name")) {
            String name = allRequestParams.get("name");
            if (!name.equals("") && !(name.length() > 50)) {
                ship.setName(allRequestParams.get("name"));
            } else {
                return null;
            }
        }
        if (allRequestParams.containsKey("planet")) {
            String planet = allRequestParams.get("planet");
            if (!planet.equals("") && !(planet.length() > 50)) {
                ship.setPlanet(allRequestParams.get("planet"));
            } else
                return null;
        }

        if (allRequestParams.containsKey("shipType")) {
            ship.setShipType(allRequestParams.get("shipType"));
        }

        if (allRequestParams.containsKey("speed")) {
            ship.setSpeed(Double.parseDouble(allRequestParams.get("speed")));
        }

        if (allRequestParams.containsKey("crewSize")) {
            int crewSize = Integer.parseInt(allRequestParams.get("crewSize"));
            if (crewSize < 10000 && crewSize > 1)
                ship.setCrewSize(crewSize);
            else
                return null;
        }

        if (allRequestParams.containsKey("prodDate")) {

            long prodDate = Long.parseLong(allRequestParams.get("prodDate"));
            if (prodDate > 0) {
                ship.setProdDate(new Date(prodDate));
            } else
                return null;
        }

        if (allRequestParams.containsKey("isUsed"))
            ship.setUsed(Boolean.parseBoolean(allRequestParams.get("isUsed")));
        else if (ship.isUsed() == null)
            ship.setUsed(false);

        ship.setRating();

        return ship;
    }
}
