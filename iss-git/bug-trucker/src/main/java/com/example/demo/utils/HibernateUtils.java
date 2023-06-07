package com.example.demo.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
    private static SessionFactory sessionFactory;
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
}
