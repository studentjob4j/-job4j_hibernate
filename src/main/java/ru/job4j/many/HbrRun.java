package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.CarBrand;
import ru.job4j.model.CarModel;
import java.util.List;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 13.02.2022
 */

public class HbrRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            CarBrand brand = CarBrand.of("Toyota");
            List<CarModel> model = List.of(CarModel.of("Prado"), CarModel.of("Crown"), CarModel.of("Land Criuser"));
            model.forEach(brand ::addCarModel);
            session.save(brand);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
