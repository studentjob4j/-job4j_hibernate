package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Author;
import ru.job4j.model.Book;
import ru.job4j.model.CarBrand;
import ru.job4j.model.CarModel;
import java.util.List;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 13.02.2022
 */

public class HbrRun {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final  SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
            new HbrRun().manyToMany();
    }

    private void oneToMany() {
        try {
            Session session = sf.openSession();
            session.beginTransaction();
            CarBrand brand = CarBrand.of("Toyota");
            List<CarModel> model = List.of(CarModel.of("Prado"), CarModel.of("Crown"), CarModel.of("Land Criuser"));
            model.forEach(brand::addCarModel);
            session.save(brand);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private void manyToMany() {
        try {
            Session session = sf.openSession();
            session.beginTransaction();
            Author one = Author.of("Tolstoy");
            Author two = Author.of("Pikul");
            Author three = Author.of("Djoan Rolling");
            Book first = Book.of("War and peace");
            Book second = Book.of("Garri Potter");
            one.add(first);
            two.add(first);
            three.add(second);
            session.persist(one);
            session.persist(two);
            session.persist(three);
            Author authorInDb = session.get(Author.class, 1);
            session.delete(authorInDb);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
