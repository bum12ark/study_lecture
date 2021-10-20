package jpql.basic;

import jpql.domain.Member;
import jpql.domain.Team;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PagingTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("jpql");
    }

    @Test
    @DisplayName("페이징 사용 예제")
    void pagingTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 임의의 멤버 생성
            for (int i = 0; i < 50; i++) {
                Member member = new Member();
                member.setName("회원" + i);
                member.setAge(i + 10);
                // 회원 등록
                entityManager.persist(member);
            }

            entityManager.flush();
            entityManager.clear();

            List<Member> results = entityManager.createQuery("SELECT m FROM Member m ORDER BY m.age DESC", Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();

            System.out.println("results = " + results);

            List<Member> resultList = entityManager.createQuery("SELECT m FROM Member m\n" +
                    "WHERE m.team = ANY (SELECT t FROM Team t)", Member.class)
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
    void afterAll() { emf.close(); }
}
