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
   @Column(name = "id")
   private int id;

   @Column(name = "name")
    private String name;

    public static CarModel of(String name) {
         CarModel model = new CarModel();
         model.setName(name);
         return model;
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
