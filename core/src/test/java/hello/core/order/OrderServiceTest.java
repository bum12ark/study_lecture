package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderServiceTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        memberService = applicationContext.getBean("getMemberService", MemberService.class);
        orderService = applicationContext.getBean("getOrderService", OrderService.class);
    }


    @Test
    public void createOrder() {
        // GIVEN
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        // WHEN
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        // THEN
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
