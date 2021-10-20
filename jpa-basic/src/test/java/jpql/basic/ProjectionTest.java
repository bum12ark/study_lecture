package jpql.basic;

import jpql.domain.Member;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectionTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("jpql");
    }

    @Test
    @DisplayName("엔티티 프로젝션은 영속성 컨텍스트에 의해 관리된다.")
    void entityProjectionTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member = new Member();
            member.setName("홍길동");
            member.setAge(10);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            List<Member> results = entityManager.createQuery("SELECT m FROM Member m", Member.class)
                    .getResultList();

            Member findMember = results.get(0);
            // update 쿼리 실행 ??
            findMember.setAge(20);

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
    @DisplayName("프로젝션 여러 타입으로 조회")
    void projectionVariousTypeSelection() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member = new Member();
            member.setName("홍길동");
            member.setAge(10);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            String sql = "SELECT m.name, m.age FROM Member m";

            // Query 타입으로 조회
            System.out.println("1. Query 타입으로 조회");
            List queryTypeResults = entityManager.createQuery(sql)
                    .getResultList();

            for (Object result : queryTypeResults) {
                Object[] resultArray = (Object[]) result;
                System.out.println("name = " + resultArray[0]);
                System.out.println("age = " + resultArray[1]);
            }

            // 2. Object[] 타입으로 조회 (Type 쿼리 사용)
            System.out.println("2. Object[] 타입으로 조회");
            List<Object[]> typeQueryResults = entityManager.createQuery(sql)
                    .getResultList();

            for (Object result : typeQueryResults) {
                Object[] resultArray = (Object[]) result;
                System.out.println("name = " + resultArray[0]);
                System.out.println("age = " + resultArray[1]);
            }

            // 3. new 명령어로 조회
            System.out.println("3. new 명령어로 조회");
            List<MemberDto> newOperationResults = 
                    entityManager.createQuery("select new jpql.MemberDto(m.name, m.age) from Member m"
                            , MemberDto.class)
                    .getResultList();

            for (MemberDto newOperationResult : newOperationResults) {
                System.out.println("newOperationResult.getName() = " + newOperationResult.getName());
                System.out.println("newOperationResult.getAge() = " + newOperationResult.getAge());
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
