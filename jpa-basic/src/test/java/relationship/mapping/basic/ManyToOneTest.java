package relationship.mapping.basic;

import org.junit.jupiter.api.*;
import relationship.mapping.basic.domain.Member;
import relationship.mapping.basic.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ManyToOneTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("mapping_basic");
    }

    @Test
    @DisplayName("객체 그래프 탐색 - 연관관계 저장과 조회")
    void saveAndFindTest() {
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
            member.setName("member1");
            member.setTeam(team); // 단반향 연관관계 설정, 참조 저장!
            entityManager.persist(member);

            // 영속성 컨텍스트를 비워 SELECT 쿼리를 보기위한 flush 처리
            entityManager.flush();
            entityManager.clear();

            // 조회
            Member findMember = entityManager.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam.getId() = " + findTeam.getId());
            
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
