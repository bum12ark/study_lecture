package relationship.mapping.inheritance;

import org.junit.jupiter.api.*;
import relationship.mapping.basic.domain.Member;
import relationship.mapping.basic.domain.Team;
import relationship.mapping.inheritance.domain.Item;
import relationship.mapping.inheritance.domain.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InheritanceTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("mapping_inheritance");
    }

    @Test
    @DisplayName("조인 전략 - 삽입, 조회 테스트")
    void joinStrategyInsertTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 무비 저장
            Movie movie = new Movie();
            movie.setDirector("봉준호");
            movie.setActor("송강호");
            movie.setName("기생충");
            movie.setPrice(10_000);
            entityManager.persist(movie);

            entityManager.flush();
            entityManager.clear();

            Movie findMovie = entityManager.find(Movie.class, movie.getId());

            assertThat(findMovie.getId()).isNotNull();

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
