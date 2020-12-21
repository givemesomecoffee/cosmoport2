package com.space.model;



import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "ship")
public class Ship {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    Long id;

    String name;

    String planet;
    @Enumerated(EnumType.STRING)
    ShipType shipType;

    Date prodDate;

    Boolean isUsed;

    Double speed;

    Integer crewSize;

    Double rating;

    public Ship() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlanet() {
        return planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public Boolean isUsed() {
        return isUsed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setShipType(String shipType) {
        this.shipType = ShipType.valueOf(shipType);
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public void setRating() {
        double k = 1.0;
        if (this.isUsed)
            k = 0.5;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime (this.prodDate);
        double rating = (80 * this.speed * k) / (3019 - Integer.parseInt(String.valueOf(calendar.get(Calendar.YEAR))) + 1);
        this.rating = (double) Math.round(rating * 100d)/ 100d;
    }

    public double getSpeed() {
        return speed;
    }

    public int getCrewSize() {
        return crewSize;
    }

    public double getRating() {
        return rating;
    }
}
