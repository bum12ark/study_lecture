package hello.core;

import hello.core.discount.*;
import hello.core.member.*;
import hello.core.order.*;

public class AppConfig {

    public MemberService getMemberService() {
        return new MemberServiceImpl(getMemberRepository());
    }

    public OrderService getOrderService() {
        return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
    }

    private MemoryMemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
    }

    private DiscountPolicy getDiscountPolicy() {
        // return new FixDiscountPolicy(); 변경 전
        return new RateDiscountPolicy(); // 변경 후
    }

}
