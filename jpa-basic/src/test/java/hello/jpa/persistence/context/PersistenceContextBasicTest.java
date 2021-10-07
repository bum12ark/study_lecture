package hello.jpa.persistence.context;

import hello.jpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersistenceContextBasicTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("hello");
    }

    @DisplayName("1차 캐시에서 조회")
    @Test
    void databaseQueryPoint() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("=== BEGIN ===");
            entityManager.persist(member);
            System.out.println("=== END ===");

            Member findMember = entityManager.find(Member.class, 100L);

            // 조회 쿼리가 나가지 않는다.
            System.out.println("findMember.getId() = " + findMember.getId());

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @DisplayName("영속 엔티티의 동일성 보장")
    @Test
    void persistenceIdentityTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member1 = entityManager.find(Member.class, 100L);
            Member member2 = entityManager.find(Member.class, 100L);

            Assertions.assertThat(member1).isSameAs(member2);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @DisplayName("엔티티 등록 - 트랜잭션을 지원하는 쓰기 지연")
    @Test
    void persistenceReadDelayTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin(); // 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야한다.

        try {
            Member memberA = new Member(1001L, "memberA");
            Member memberB = new Member(1002L, "memberB");

            entityManager.persist(memberA);
            entityManager.persist(memberB);
            // 여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.

            // 커밋하는 순간 데이터 베이스에 INSERT SQL을 보낸다.
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @DisplayName("엔티티 수정 - 변경 감지")
    @Test
    void persistenceDirtyCheckTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin(); // 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야한다.

        try {
            // 영속 엔티티 조회
            Member memberA = entityManager.find(Member.class, 1L);

            // 영속 엔티티 데이터 수정
            memberA.setName("modifyMember");

            /** 이런 코드가 있어야 하지 않을까? **/
            // entityManager.persist(memberA);

            transaction.commit(); // [트랜잭션] 커밋
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @DisplayName("엔티티 삭제")
    @Test
    void persistenceRemove() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin(); // 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야한다.

        try {
            Member member = entityManager.find(Member.class, 1L);

            entityManager.remove(member);

            transaction.commit(); // [트랜잭션] 커밋
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
