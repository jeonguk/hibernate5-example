package com.jeonguk;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeonguk.entity.Person;
import com.jeonguk.util.JPAUtil;

/**
 * EntityManager#remove()
 * - 이 메소드는 엔티티 / 오브젝트를 데이터베이스에서 제거하는 데 사용됩니다. 
 * 엔터티가 데이터베이스에 존재하지 않으면 예외를 throw합니다.
 * @author jeonguk
 *
 */
public class DeletePersonExample {
    
    private static Logger logger = LoggerFactory.getLogger(DeletePersonExample.class);
    
    public static void main(String[] args) {

        EntityManager entityManager = null;
        try {
           entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
           entityManager.getTransaction().begin();

           Person person = entityManager.find(Person.class, 1l);
           
           //Remove entity
           entityManager.remove(person);
           
           entityManager.getTransaction().commit();
           logger.info("Person removed successfully");
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