package com.jeonguk;

import java.util.Calendar;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeonguk.entity.Person;
import com.jeonguk.util.JPAUtil;

/**
 * EntityManager#merge()
 * - 이 메소드는 엔티티의 상태를 분리에서 지속으로 변경하는 데 사용됩니다.
 * 이 방법을 사용하여 데이터베이스의 엔티티를 수정할 수 있습니다.
 * @author jeonguk
 */
public class UpdatePersonExample {

    private static Logger logger = LoggerFactory.getLogger(UpdatePersonExample.class);
    
    public static void main(String[] args) {

        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Person person = entityManager.find(Person.class, 1l);

            // Detach entity
            entityManager.clear();

            // Update DOB
            Calendar dob = Calendar.getInstance();
            dob.setTime(person.getDob());
            dob.set(Calendar.YEAR, 1847);

            person.setDob(dob.getTime());

            entityManager.merge(person);

            entityManager.getTransaction().commit();

            logger.info("Person updated successfully");
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