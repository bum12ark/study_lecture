package hello.jpa.persistence.context;

import hello.jpa.entity.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@TestInstance(Lifecycle.PER_CLASS)
public class DetachTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("hello");
    }

    @Test
    @DisplayName("준영속상태로 만들기")
    void detachedTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member = entityManager.find(Member.class, 200L);
            member.setName("modifiedMember");

            entityManager.detach(member); // 방법 1
            entityManager.clear(); // 방법 2

            System.out.println("======= after detach =======");

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @AfterAll
    void afterAll() {
        emf.close();
    }
}
