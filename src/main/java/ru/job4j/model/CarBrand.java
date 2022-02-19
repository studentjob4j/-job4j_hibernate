package ru.job4j.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 13.02.2022
 */

@Entity
@Table(name = "car_brand")
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "carBrand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarModel> models;

    public static CarBrand of(String name) {
        CarBrand brand = new CarBrand();
        brand.setName(name);
        return brand;
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

    public void addCarModel(CarModel model) {
        if (models == null) {
            models = new ArrayList<>();
        }
        models.add(model);
    }

    public List<CarModel> getList() {
        return models;
    }

    public void setList(List<CarModel> list) {
        this.models = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarBrand brand = (CarBrand) o;
        return id == brand.id && Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
