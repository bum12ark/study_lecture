package relationship.mapping.proxy;

import org.junit.jupiter.api.*;
import relationship.mapping.proxy.domain.Member;
import relationship.mapping.proxy.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProxyTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("mapping_proxy");
    }

    @Test
    @DisplayName("getReference 프록시 객체 테스트")
    void getReferenceTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 임의의 Member 객체 생성
            Member member = new Member();
            member.setName("홍길동");

            entityManager.persist(member);

            // Member 객체 저장 쿼리 발생
            entityManager.flush();
            entityManager.clear();

            // getReference 를 호출하는 시점에는 쿼리 호출 X
            System.out.println("===call getReference!===");
            Member proxyMember = entityManager.getReference(Member.class, member.getId());

            // 해당 객체를 사용할 때 쿼리 호출
            System.out.println("===call proxyMember.getName()===");
            String name = proxyMember.getName();
            System.out.println("proxyMember = " + proxyMember.getClass());

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
    @DisplayName("프록시 엔티티 비교시 == 대신 instance of 사용")
    void proxyInstanceOfTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 임의의 Member 객체 생성
            Member member1 = new Member();
            member1.setName("홍길동");
            entityManager.persist(member1);

            Member member2 = new Member();
            member2.setName("김유신");
            entityManager.persist(member2);

            // Member 객체 저장 쿼리 발생
            entityManager.flush();
            entityManager.clear();

            // Member 객체 반환
            Member findMember1 = entityManager.find(Member.class, member1.getId());
            // Proxy 객체 반환
            Member findMember2 = entityManager.getReference(Member.class, member2.getId());

            // == 비교
            boolean sameCompare = findMember1.getClass() == findMember2.getClass();
            // instance of 비교
            boolean instanceOfCompare = (findMember1 instanceof Member);

            assertThat(sameCompare).isFalse();
            assertThat(instanceOfCompare).isTrue();

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
    @DisplayName("영속성 컨텍스트에 찾는 엔티티가 있는 경우, 프록시가 아닌 실제 객체 반환")
    void persistenceContextProxyTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 임의의 Member 객체 생성
            Member member1 = new Member();
            member1.setName("홍길동");
            entityManager.persist(member1);

            // Member 객체 저장 쿼리 발생
            entityManager.flush();
            entityManager.clear();

            // Member 객체 반환
            Member findMember = entityManager.find(Member.class, member1.getId());
            // 프록시 객체 반환 ?
            Member reference = entityManager.getReference(Member.class, member1.getId());

            // 프록시 객체 반환이 아닌 실제 Entity 반환
            assertThat(reference.getClass()).isSameAs(Member.class);
            // find 객체와 reference 객체가 동일
            assertThat(reference).isSameAs(findMember);
            // 동일한 트랜잭션에서 조회한 엔티티는 == 비교 시 true를 반환
            assertThat(findMember == reference).isTrue();

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
