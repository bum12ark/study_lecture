package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "memberService")
    public MemberService getMemberService() {
        System.out.println("call AppConfig.getMemberService");
        return new MemberServiceImpl(getMemberRepository());
    }

    @Bean(name = "orderService")
    public OrderService getOrderService() {
        System.out.println("call AppConfig.getOrderService");
        return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
    }

    @Bean(name = "memberRepository")
    public MemberRepository getMemberRepository() {
        System.out.println("call AppConfig.getMemberRepository");
        return new MemoryMemberRepository();
    }

    @Bean(name = "discountPolicy")
    public DiscountPolicy getDiscountPolicy() {
        return new RateDiscountPolicy();
    }

}
