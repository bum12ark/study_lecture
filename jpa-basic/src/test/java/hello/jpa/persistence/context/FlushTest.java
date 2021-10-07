package hello.jpa.persistence.context;

import hello.jpa.entity.Member;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlushTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("hello");
    }

    @Test
    @DisplayName("플러시가 발생될 때 - 플러시 강제 호출")
    void forcedExecutionFlushTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member = new Member(200L, "member200");
            entityManager.persist(member);

            System.out.println("====== flush before ======");

            entityManager.flush();

            System.out.println("====== flush after & commit before ======");

            transaction.commit();

            System.out.println("====== commit after ======");
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
