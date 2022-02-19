package ru.job4j.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 13.02.2022
 */

@Entity
@Table(name = "car_model")
public class CarModel {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   private String name;

    @ManyToOne
    private CarBrand carBrand;

    public static CarModel of(String name) {
         CarModel model = new CarModel();
         model.setName(name);
         return model;
    }

    public static CarModel of(String name, CarBrand brand) {
        CarModel model = new CarModel();
        model.setName(name);
        model.setCarBrand(brand);
        return model;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carmodel = (CarModel) o;
        return id == carmodel.id && Objects.equals(name, carmodel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
