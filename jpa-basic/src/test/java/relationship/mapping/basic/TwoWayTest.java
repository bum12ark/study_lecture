package relationship.mapping.basic;

import org.junit.jupiter.api.*;
import relationship.mapping.basic.domain.Member;
import relationship.mapping.basic.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TwoWayTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("mapping_basic");
    }

    @Test
    @DisplayName("양방향 연관관계 조회 테스트")
    void twoWaySearchTest() {
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
            Team findTeam = entityManager.find(Team.class, team.getId());
            int memberSize = findTeam.getMembers().size();

            System.out.println("memberSize = " + memberSize); // 역방향 조회

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    @DisplayName("양방향 매핑 실수 - 연관관계 주인에 값을 입력하지 않음")
    void mappingPopularFault() {
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
            // 역방향(주인이 아닌 방향)만 연관관계 설정
            team.getMembers().add(member);
            entityManager.persist(member);

            // 영속성 컨텍스트를 비우기
            entityManager.flush();
            entityManager.clear();

            Member findMember = entityManager.find(Member.class, member.getId());

            // memberId가 입력되지 않음!!
            Assertions.assertThrows(NullPointerException.class, () -> {
                findMember.getTeam().getId();
            });

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    @DisplayName("양방향 매핑 - 연관관계의 양쪽에 값 입력")
    void mappingBothInsertValue() {
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
            // 아래 라인이 없어도 정상 동작 (객체지향적 관점에서 양쪽에 다 값을 입력하는 것이 좋다)
            team.getMembers().add(member);
            // 연관관계의 주인에 값 설정
            member.setTeam(team);
            entityManager.persist(member);

            // 영속성 컨텍스트를 비우기
            entityManager.flush();
            entityManager.clear();

            Member findMember = entityManager.find(Member.class, member.getId());

            assertThat(findMember.getTeam().getId()).isNotNull();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    @DisplayName("양방향 매핑 - 연관관계의 주인에민 값 입력")
    void mappingOwnerInsertValue() {
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
            // team.getMembers().add(member);
            // 연관관계의 주인에 값 설정 (연관관계 편의메소드 X)
            member.setTeam(team);
            entityManager.persist(member);

            Team findTeam = entityManager.find(Team.class, team.getId()); // 1차 캐시에서 조회

            for (Member findMember : findTeam.getMembers()) {
                // 1차 캐시에서 조회하기 때문에 팀에서 회원이 조회되지 않는다.
                assertThat(findMember.getName()).isNull();
            }

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
