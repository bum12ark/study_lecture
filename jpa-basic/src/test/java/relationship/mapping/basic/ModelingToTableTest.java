package relationship.mapping.basic;

import org.junit.jupiter.api.*;
import relationship.mapping.basic.domain.Member;
import relationship.mapping.basic.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModelingToTableTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("mapping_basic");
    }

    @Test
    @DisplayName("객체를 테이블에 맞추어 모델링 - 식별자로 다시 조회")
    void modelingToTableTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 팀 저장
            Team team = new Team();
            team.setName("TeamA");
            entityManager.persist(team);

            // 회원 저장
            Member member = new Member();
            member.setName("memberA");
            member.setTeamId(team.getId()); // 객체지향스럽지 못하다!!
            entityManager.persist(member);

            // 연관관계가 없기 때문에 외래키를 사용하여 다시 조회해야하는 불편함이 존재한다.
            Member findMember = entityManager.find(Member.class, member.getId());
            Long teamId = findMember.getTeamId();
            Team findTeam = entityManager.find(Team.class, teamId);

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
