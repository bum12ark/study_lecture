package relationship.mapping.various;

import org.junit.jupiter.api.*;
import relationship.mapping.various.domain.Member;
import relationship.mapping.various.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OneToManyTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("mapping_various");
    }

    @Test
    @DisplayName("@OneToMany 일대다 단방향")
    void onToManyOneWayTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 회원 저장
            Member member = new Member();
            member.setName("member1");
            entityManager.persist(member);

            // 팀 저장
            Team team = new Team();
            team.setName("TeamA");
            // UPDATE 쿼리가 추가적으로 발생한다. (성능 단점)
            team.getMembers().add(member);
            entityManager.persist(team);

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
