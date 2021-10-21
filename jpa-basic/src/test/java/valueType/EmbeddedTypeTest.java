package valueType;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import valueType.domain.Member;
import valueType.domain.embedded.Address;
import valueType.domain.embedded.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmbeddedTypeTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("value_type");
    }

    @Test
    @DisplayName("임베디드 타입 테스트")
    void embeddedTypeTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member = new Member();
            member.setName("홍길동");
            member.setAddress(
                    new Address("서울시", "서대문로", "123-456")
            );
            member.setPeriod(
                    new Period(LocalDateTime.of(2021, 04, 01, 15, 10),
                            LocalDateTime.now())
            );

            entityManager.persist(member);

            boolean workTimeValid = member.getPeriod().workTimeValidation();
            assertThat(workTimeValid).isTrue();

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
    @DisplayName("값 타입을 공유할 경우 문제점")
    void valueTypeShareProblemTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Address address = new Address("서울시", "서대문로", "123-456");

            Member memberA = new Member();
            memberA.setName("홍길동");
            memberA.setAddress(address);
            entityManager.persist(memberA);

            Member memberB = new Member();
            memberB.setName("김유신");
            memberB.setAddress(address);
            entityManager.persist(memberB);

            // memberA의 address 만 변경하고 싶어!
            memberA.getAddress().setCity("전주시");

            entityManager.flush();
            entityManager.clear();

            Member findMemberA = entityManager.find(Member.class, memberA.getId());
            Member findMemberB = entityManager.find(Member.class, memberB.getId());

            String findMemberACity = findMemberA.getAddress().getCity();
            String findMemberBCity = findMemberB.getAddress().getCity();

            // MemberA 와 memberB가 같다고 나옴
            assertThat(findMemberACity).isEqualTo(findMemberBCity);

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
    @DisplayName("값 타입은 복사하여 사용")
    public void valueTypeCopyTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Address address = new Address("서울시", "서대문로", "123-456");

            Member memberA = new Member();
            memberA.setName("홍길동");
            memberA.setAddress(address);
            entityManager.persist(memberA);

            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Member memberB = new Member();
            memberB.setName("김유신");
            memberB.setAddress(copyAddress);
            entityManager.persist(memberB);

            // memberA의 address 만 변경하고 싶어!
            memberA.getAddress().setCity("전주시");

            entityManager.flush();
            entityManager.clear();

            Member findMemberA = entityManager.find(Member.class, memberA.getId());
            Member findMemberB = entityManager.find(Member.class, memberB.getId());

            String findMemberACity = findMemberA.getAddress().getCity();
            String findMemberBCity = findMemberB.getAddress().getCity();

            // MemberA 와 memberB가 같지 않음!
            assertThat(findMemberACity).isNotEqualTo(findMemberBCity);

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
