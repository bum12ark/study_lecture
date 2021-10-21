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
public class FetchJoinTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("jpql");
    }

    void insertFetchJoinData(EntityManager entityManager) {
        Team teamA = new Team("팀A");
        entityManager.persist(teamA);
        Team teamB = new Team("팀B");
        entityManager.persist(teamB);
        Team teamC = new Team("팀C");
        entityManager.persist(teamC);

        Member member1 = new Member("회원1", 10);
        member1.changeTeam(teamA);
        entityManager.persist(member1);
        Member member2 = new Member("회원2", 20);
        member2.changeTeam(teamA);
        entityManager.persist(member2);
        Member member3 = new Member("회원3", 30);
        member3.changeTeam(teamC);
        entityManager.persist(member3);
        Member member4 = new Member("회원4", 40);
        entityManager.persist(member4);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("페치 조인 예제 시나리오")
    void fetchJoinScenarioTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            this.insertFetchJoinData(entityManager);

            String query = "SELECT m FROM Member m JOIN FETCH m.team";
            List<Member> results = entityManager.createQuery(query, Member.class)
                    .getResultList();

            for (Member member : results) {
                System.out.println("member.getName() = " + member.getName());
                if (member.getTeam() != null) {
                    System.out.println("entityManager.contains(member.getTeam()) = " +
                            entityManager.contains(member.getTeam()));
                    System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
                } else {
                    System.out.println("member.getTeam() = null");
                }
            }

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
    @DisplayName("일대다 관계, 컬렉션 페치 조인 테스트")
    void collectionFetchJoinTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            this.insertFetchJoinData(entityManager);

            String query = "SELECT t FROM Team t JOIN FETCH t.members";
            List<Team> results = entityManager.createQuery(query, Team.class)
                    .getResultList();

            System.out.println("results.size() = " + results.size());
            for (Team team : results) {
                for (Member member : team.getMembers()) {
                    System.out.println("[" + team + "] " + team.getName() + " = " + member);
                }
            }

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
    @DisplayName("DISTINCT 페치 조인 테스트")
    void distinctFetchJoinTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            this.insertFetchJoinData(entityManager);

            String query = "SELECT DISTINCT t FROM Team t JOIN FETCH t.members";
            List<Team> results = entityManager.createQuery(query, Team.class)
                    .getResultList();

            System.out.println("results.size() = " + results.size());
            for (Team team : results) {
                for (Member member : team.getMembers()) {
                    System.out.println("[" + team + "] " + team.getName() + " = " + member);
                }
            }

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
