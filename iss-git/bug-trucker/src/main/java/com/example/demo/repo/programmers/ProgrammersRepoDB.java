package com.example.demo.repo.programmers;

import com.example.demo.model.Programmer;
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

public class ProgrammersRepoDB implements ProgrammersRepo{
    private static SessionFactory sessionFactory;

    private static HibernateUtils hibernateUtils;

    public ProgrammersRepoDB() {}

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
    public List<Programmer> getAll() {
        SessionFactory ses = HibernateUtils.getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                List<Programmer> programmers = session.createQuery("FROM Programmer ", Programmer.class).list();
                System.out.println(programmers.size() + " programmers");
                transact.commit();
                return programmers;
            } catch (Exception e) {
                System.err.println("Error when getting all the programmers " + e);
                if (transact != null) {
                    transact.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(Programmer entity) throws IOException {
        return false;
    }

    @Override
    public Programmer update(Programmer entity) throws IOException {
        return null;
    }

    @Override
    public Programmer save(Programmer entity) throws IOException {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Programmer findByUsernamePwd(String username, String password) {
        SessionFactory ses = HibernateUtils.getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                Query<Programmer> query = session.createQuery("FROM Programmer WHERE username=:username AND password=:password");
                query.setParameter("username", username);
                query.setParameter("password", password);
                Programmer prog = query.getSingleResult();
                System.out.println("Found programmer " + prog.toString());
                transact.commit();
                return prog;
            } catch (Exception e) {
                System.err.println("Error when getting the programmer " + e);
                if (transact != null) {
                    transact.rollback();
                }
            }
        }
        return null;
    }
}
