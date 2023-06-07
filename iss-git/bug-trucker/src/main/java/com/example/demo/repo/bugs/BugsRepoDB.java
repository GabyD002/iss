package com.example.demo.repo.bugs;

import com.example.demo.model.Bug;
import com.example.demo.model.BugStatus;
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

public class BugsRepoDB implements BugsRepo {
    private static SessionFactory sessionFactory;
    private static HibernateUtils hibernateUtils;

    public static SessionFactory getSession() {
        try {
            if (sessionFactory == null || sessionFactory.isClosed())
                sessionFactory = getNewSession();
        } catch (Exception e) {
            System.out.println("Error DB " + e);
        }
        return sessionFactory;
    }

    public static SessionFactory getNewSession() {
        SessionFactory ses = null;
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            ses = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error getting connection " + e);
        }
        return ses;
    }

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

    public BugsRepoDB() {
    }

    @Override
    public List<Bug> getAll() {
        SessionFactory ses = HibernateUtils.getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                List<Bug> bugs = session.createQuery("FROM Bug ", Bug.class).list();
                System.out.println(bugs.size() + " bugs");
                transact.commit();
                return bugs;
            } catch (Exception e) {
                System.err.println("Error when getting all the bugs " + e);
                if (transact != null) {
                    transact.rollback();
                }
                throw new RuntimeException("Failed to retrieve bugs", e);
            }
        }
        //return Collections.emptyList();
    }

    @Override
    public boolean delete(Bug entity) throws IOException {
        return false;
    }

    @Override
    public Bug update(Bug entity) throws IOException {
        SessionFactory ses = getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                String hql = "UPDATE Bug SET name=:bugname, description=:bugdesc WHERE id=:bugid";
                Query query = session.createQuery(hql);
                query.setParameter("bugname", entity.getName());
                query.setParameter("bugdesc", entity.getDescription());
                query.setParameter("bugid", entity.getId());
                int result = query.executeUpdate();
                System.out.println("Rows affected " + result);
                transact.commit();
            } catch (Exception e) {
                System.err.println("Error when updating the bug " + e);
                if (transact != null) {
                    transact.rollback();
                }
            }
        }
        return entity;
    }

    @Override
    public Bug save(Bug entity) throws IOException {
        SessionFactory ses = HibernateUtils.getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                session.save(entity);
                transact.commit();
            } catch (Exception e) {
                System.err.println("Error when adding a bug " + e);
                if (transact != null) {
                    transact.rollback();
                }
                throw new RuntimeException("Failed to add bug", e);
            }
        }
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Bug findByID(int id) {
        SessionFactory ses = HibernateUtils.getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                Query<Bug> query = session.createQuery("FROM Bug WHERE id=:id");
                query.setParameter("id", id);
                Bug bug = query.getSingleResult();
                System.out.println("Found the requested bug " + bug.toString());
                transact.commit();
                return bug;
            } catch (Exception e) {
                System.err.println("Error when getting the bug " + e);
                if (transact != null) {
                    transact.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public boolean updateExperimental(int id, String name, String description) {
        SessionFactory ses = getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                String hql = "UPDATE Bug SET name=:bugname, description=:bugdesc WHERE id=:bugid";
                Query query = session.createQuery(hql);
                query.setParameter("bugname", name);
                query.setParameter("bugdesc", description);
                query.setParameter("bugid", id);
                int result = query.executeUpdate();
                System.out.println("Rows affected " + result);
                transact.commit();
                return true;
            } catch (Exception e) {
                System.err.println("Error when updating the bug " + e);
                if (transact != null) {
                    transact.rollback();
                }
            }
        }
        return false;
    }

    @Override
    public boolean solveBug(int id, BugStatus status) {
        SessionFactory ses = getSession();
        try (Session session = ses.openSession()) {
            Transaction transact = null;
            try {
                transact = session.beginTransaction();
                String hql = "UPDATE Bug SET status=:status WHERE id=:id";
                Query query = session.createQuery(hql);
                query.setParameter("status", status);
                query.setParameter("id", id);
                int result = query.executeUpdate();
                System.out.println("Rows affected " + result);
                transact.commit();
                return true;
            } catch (Exception e) {
                System.err.println("Error when updating the bug " + e);
                if (transact != null) {
                    transact.rollback();
                }
            }
        }
        return false;
    }
}
