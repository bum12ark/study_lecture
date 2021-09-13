package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);

        Member member = new Member(1L, "userA", Grade.VIP);
        int fixDiscountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        int rateDiscountPrice = discountService.discount(member, 10000, "rateDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(fixDiscountPrice).isEqualTo(1000); // 고정 정책의 경우 1000원 할인
        assertThat(rateDiscountPrice).isEqualTo(1000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> discountPolicyMap; // key: 빈이름, 객체
        private final List<DiscountPolicy> discountPolicies;

        public DiscountService(Map<String, DiscountPolicy> discountPolicyMap, List<DiscountPolicy> discountPolicies) {
            this.discountPolicyMap = discountPolicyMap;
            this.discountPolicies = discountPolicies;

            System.out.println("discountPolicyMap = " + discountPolicyMap);
            System.out.println("discountPolicies = " + discountPolicies);
        }

        // 할인 코드를 빈 이름과 매칭 (다형성 코드 완성)
        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = discountPolicyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}
