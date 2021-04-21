package edu.mum.cs.cs544.exercises;

import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AppBook {

    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static void main(String[] args) {
        // Hibernate placeholders
        Session session = null;
        Transaction tx = null;
        
        //Exercise-1 Create 3 books
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            
            Book book1 = new Book("Java", "SDA231", "Paymen Salek", 15.9, new Date(200000000));
            session.persist(book1);
            Book book2 = new Book("Cooking", "SDH234", "Battuul", 12, new Date(700000000));
            session.persist(book2);
            Book book3 = new Book("Cars", "CAR532", "Ochirgarid", 21.7, new Date(1200000000));
            session.persist(book3);            
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        //Exercise-2 Retrieve books and print to console
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            @SuppressWarnings("unchecked")
            List<Book> bookList = session.createQuery("from Book").list();
            for (Book book : bookList) {
            	System.out.println("------------------------------");
                System.out.println("title: " + book.getTitle() + ", author: "
                        + book.getAuthor() + ", ISBN: " + book.getISBN() + 
                        ", price: " + book.getPrice() + ", published date: " + book.getPublish_date());
            }
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        //Exercise-3 Changing 1 book info, and deleting 1 book
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Book book1 = (Book)session.get(Book.class, 1);
            book1.setTitle("Enterprice Architecture");
            book1.setPrice(45.99);
            session.update(book1);
            
            Book book2 = (Book) session.get(Book.class, 2);
            session.delete(book2);
            
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        
        //Exercise-2 Retrieve books and print to console
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            @SuppressWarnings("unchecked")
            List<Book> bookList = session.createQuery("from Book").list();
            for (Book book : bookList) {
            	System.out.println("------------------------------");
                System.out.println("title: " + book.getTitle() + ", author: "
                        + book.getAuthor() + ", ISBN: " + book.getISBN() + 
                        ", price: " + book.getPrice() + ", published date: " + book.getPublish_date());
            }
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        
        // Close the SessionFactory (not mandatory)
        sessionFactory.close();
        System.exit(0);
    }

}
