package hello.core;

import hello.core.discount.*;
import hello.core.member.*;
import hello.core.order.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "memberService")
    public MemberService getMemberService() {
        return new MemberServiceImpl(getMemberRepository());
    }

    @Bean(name = "orderService")
    public OrderService getOrderService() {
        return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
    }

    @Bean(name = "memoryMemberRepository")
    public MemoryMemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean(name = "discountPolicy")
    public DiscountPolicy getDiscountPolicy() {
        return new RateDiscountPolicy();
    }

}
