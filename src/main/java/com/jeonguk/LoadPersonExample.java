package com.jeonguk;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeonguk.entity.Person;
import com.jeonguk.util.JPAUtil;

/**
 * EntityManager#find()
 * - 이 메소드는 엔티티를 지정된 기본 키에 대한 데이터베이스로 검색하는 데 사용됩니다.
 * @author jeonguk
 */
public class LoadPersonExample {
    
    private static Logger logger = LoggerFactory.getLogger(LoadPersonExample.class);
    
    public static void main(String[] args) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Person person = entityManager.find(Person.class, 1l);
            logger.info("First Name = " + person.getFirstName());
            logger.info("Middle Name = " + person.getMiddleName());
            logger.info("Last Name = " + person.getLastName());
            logger.info("DOB = " + new SimpleDateFormat("yyyy-MM-dd").format(person.getDob()));

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Person removed ", e);
            if (entityManager != null) {
                logger.info("Transaction is being rolled back.");
                entityManager.getTransaction().rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        JPAUtil.shutdown();
    }

}