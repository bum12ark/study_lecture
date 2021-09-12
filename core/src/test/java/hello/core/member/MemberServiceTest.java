package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Description;

public class MemberServiceTest {

    MemberService memberService;

    // AppConfig 클래스의 Bean 정보들을 스프링 컨테이너에 관리한다.
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

    @BeforeEach
    public void beforeEach() {
        memberService = applicationContext.getBean("memberService", MemberService.class);
    }

    @Test
    @Description("회원 가입 테스트")
    public void join() {
        // given
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then
        Assertions.assertThat(member).isEqualTo(findMember);
    }

}
