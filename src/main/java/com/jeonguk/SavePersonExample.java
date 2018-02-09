package com.jeonguk;

import java.util.Calendar;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeonguk.entity.Person;
import com.jeonguk.util.JPAUtil;

/**
 * EntityManager#persist()
 * - 이 메소드는 엔티티 / 오브젝트를 데이터베이스에 저장하고 void를 리턴하는 데 사용됩니다. 
 * 엔티티가 이미 데이터베이스에 존재하면 예외가 발생합니다.
 * @author jeonguk
 */
public class SavePersonExample {
    
    private static Logger logger = LoggerFactory.getLogger(SavePersonExample.class);
    
    public static void main(String[] args) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Person person = new Person();
            person.setFirstName("Thomas");
            person.setMiddleName("Alva");
            person.setLastName("Edison");
            // Set DOB
            Calendar dob = Calendar.getInstance();
            dob.set(Calendar.DATE, 11);
            dob.set(Calendar.MONTH, 02);
            dob.set(Calendar.YEAR, 1947);
            dob.set(Calendar.HOUR_OF_DAY, 0);
            dob.set(Calendar.MINUTE, 0);
            dob.set(Calendar.SECOND, 0);
            dob.set(Calendar.MILLISECOND, 0);
            person.setDob(dob.getTime());

            // Save entity
            entityManager.persist(person);

            entityManager.getTransaction().commit();
            logger.info("Person saved successfully");
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