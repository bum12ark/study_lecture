package hello.jpa;

import hello.jpa.entity.Member;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JpaMainTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("hello");
    }

    @DisplayName("Jpa 기본 실행 테스트")
    @Test
    void jpaBasicImplement() {
        EntityManager entityManager = emf.createEntityManager();

        boolean open = entityManager.isOpen();

        assertThat(open).isTrue();

        entityManager.close();
    }

    @DisplayName("Member 단건 Insert 테스트")
    @Test
    void insertOneRowToMember() {
        EntityManager entityManager = emf.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member = new Member();
            member.setId(9999L);
            member.setUserName("Hello JPA");

            entityManager.persist(member);

            transaction.commit();
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