package com.klu.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // I. Insert records
            Project project1 = new Project("Project A", 12, 50000, "Alice");
            Project project2 = new Project("Project B", 6, 30000, "Bob");
            Project project3 = new Project("Project C", 18, 70000, "Charlie");

            session.save(project1);
            session.save(project2);
            session.save(project3);

            transaction.commit();

            // II. Aggregate functions using Criteria
            Criteria criteria = session.createCriteria(Project.class);

            // Count
            criteria.setProjection(Projections.rowCount());
            Long count = (Long) criteria.uniqueResult();
            System.out.println("Count of projects: " + count);

            // Maximum budget
            criteria.setProjection(Projections.max("budget"));
            Double maxBudget = (Double) criteria.uniqueResult();
            System.out.println("Maximum budget: " + maxBudget);

            // Minimum budget
            criteria.setProjection(Projections.min("budget"));
            Double minBudget = (Double) criteria.uniqueResult();
            System.out.println("Minimum budget: " + minBudget);

            // Sum of budgets
            criteria.setProjection(Projections.sum("budget"));
            Double totalBudget = (Double) criteria.uniqueResult();
            System.out.println("Sum of budgets: " + totalBudget);

            // Average budget
            criteria.setProjection(Projections.avg("budget"));
            Double avgBudget = (Double) criteria.uniqueResult();
            System.out.println("Average budget: " + avgBudget);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
