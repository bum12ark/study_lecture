package jpql.itermediate;

import jpql.domain.Member;
import jpql.domain.Team;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NamedQueryTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("jpql");
    }

    @Test
    void namedQueryTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            List<Member> results = entityManager.createNamedQuery("Member.findByName", Member.class)
                    .setParameter("name", "회원1")
                    .getResultList();

            // 트랜잭션 커밋
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
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
