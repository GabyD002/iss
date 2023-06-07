package com.example.demo.repo.Tester;

import com.example.demo.model.Tester;
import com.example.demo.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class TesterRepository implements TesterRepositoryI {
    private static SessionFactory sessionFactory;

    private static HibernateUtils hibernateUtils;

    public TesterRepository() {}

    public static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public List<Tester> getAll() {
        SessionFactory ses = HibernateUtils.getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                List<Tester> testers = session.createQuery("FROM Tester ", Tester.class).list();
                System.out.println(testers.size() + " qas");
                transact.commit();
                return testers;
            } catch (Exception e) {
                System.err.println("Error when getting all the qas " + e);
                if (transact != null) {
                    transact.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(Tester entity) throws IOException {
        return false;
    }

    @Override
    public Tester update(Tester entity) throws IOException {
        return null;
    }

    @Override
    public Tester save(Tester entity) throws IOException {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Tester findByUsernamePwd(String username, String password) {
        SessionFactory ses = HibernateUtils.getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                Query<Tester> query = session.createQuery("FROM Tester WHERE username=:username AND password=:password");
                query.setParameter("username", username);
                query.setParameter("password", password);
                Tester tester = query.getSingleResult();
                System.out.println("Found QA " + tester.toString());
                transact.commit();
                return tester;
            } catch (Exception e) {
                System.err.println("Error when getting the QA " + e);
                if (transact != null) {
                    transact.rollback();
                }
            }
        }
        return null;
    }
}
