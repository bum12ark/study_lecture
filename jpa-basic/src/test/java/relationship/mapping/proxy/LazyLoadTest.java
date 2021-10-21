package relationship.mapping.proxy;

import org.junit.jupiter.api.*;
import relationship.mapping.proxy.domain.EagerMember;
import relationship.mapping.proxy.domain.Member;
import relationship.mapping.proxy.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LazyLoadTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("mapping_proxy");
    }

    @Test
    @DisplayName("ManyToOne 객체 지연 로딩 LAZY를 사용해서 프록시로 조회")
    void manyToOneFetchTypeLazy() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 임의의 Member 객체 생성
            Team team = new Team();
            team.setName("teamA");
            entityManager.persist(team);

            Member member = new Member();
            member.setName("홍길동");
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            Member findMember = entityManager.find(Member.class, member.getId());

            System.out.println(findMember.getTeam().getClass());

            // 트랜잭션 커밋
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    @DisplayName("ManyToOne 객체 즉시 로딩 EAGER를 사용하여 엔티티 객체 반환")
    void manyToOneFetchTypeEager() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 임의의 Member 객체 생성
            Team team = new Team();
            team.setName("teamA");
            entityManager.persist(team);

            EagerMember member = new EagerMember();
            member.setName("홍길동");
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            EagerMember findMember = entityManager.find(EagerMember.class, member.getId());

            System.out.println(findMember.getTeam().getClass());

            // 트랜잭션 커밋
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    @DisplayName("즉시로딩 N+1 문제 테스트")
    void jpqlNPlusOneTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 임의의 Member 객체 생성
            Team team = new Team();
            team.setName("teamA");
            entityManager.persist(team);

            EagerMember member = new EagerMember();
            member.setName("홍길동");
            member.setTeam(team);
            entityManager.persist(member);

            Team teamB = new Team();
            team.setName("teamB");
            entityManager.persist(teamB);

            EagerMember memberB = new EagerMember();
            memberB.setName("김유신");
            memberB.setTeam(teamB);
            entityManager.persist(memberB);

            entityManager.flush();
            entityManager.clear();

            List<EagerMember> results = entityManager.createQuery("select m from EagerMember m", EagerMember.class)
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
