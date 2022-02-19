package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

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
            new HbrRun().uniDirectionalOneToMany();
    }

    private void oneToMany() {
        executeTransaction(session -> {
            CarBrand brand = CarBrand.of("Toyota");
            List<CarModel> model = List.of(CarModel.of("Prado"), CarModel.of("Crown"), CarModel.of("Land Criuser"));
            model.forEach(brand::addCarModel);
            session.save(brand);
        });
    }

    private void manyToMany() {
        executeTransaction(session -> {
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
        });
    }

    private void uniDirectionalOneToMany() {
        executeTransaction(session -> {
            CarBrand toyota = CarBrand.of("Toyota");
            List<CarModel> toyotaModels = List.of(
                    CarModel.of("Hilander", toyota),
                    CarModel.of("Prado", toyota),
                    CarModel.of("Mark x", toyota)
            );
            toyotaModels.forEach(toyota::addCarModel);
            session.save(toyota);

            CarBrand nissan = CarBrand.of("Nissan");
            List<CarModel> nissanModels = List.of(
                    CarModel.of("Qashqai", nissan),
                    CarModel.of("Murano", nissan)
            );
            nissanModels.forEach(nissan::addCarModel);
            session.save(nissan);
        });
        /* simple transaction */
        executeTransaction(session -> {
            List<CarBrand> brands = session.createQuery("from CarBrand").list();
            brands.stream()
                    .map(CarBrand::getList)
                    .flatMap(Collection::stream)
                    .forEach(model -> System.out.println(model.getName()));
        });
        /* join fetch */
        List<CarBrand> brands = new ArrayList<>();
        executeTransaction(session -> {
            List list = session.createQuery(
                    "select distinct brand from CarBrand brand join fetch brand.models"
            ).list();
            brands.addAll(list);
        });
        brands.stream()
                .map(CarBrand::getList)
                .flatMap(Collection::stream)
                .forEach(model -> System.out.println(model.getName()));
    }

    private void executeTransaction(final Consumer<Session> command) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            command.accept(session);
            transaction.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
