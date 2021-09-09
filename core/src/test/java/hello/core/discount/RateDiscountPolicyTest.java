package hello.core.discount;


import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    public void vipOk() {
        // GIVEN
        Member memberVIP = new Member(1L, "memberVIP", Grade.VIP);
        // WHEN
        int discount = discountPolicy.discount(memberVIP, 10000);
        // THEN
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다.")
    public void vipFail() throws Exception {
        // GIVEN
        Member memberVIP = new Member(2L, "memberVIP", Grade.BASIC);
        // WHEN
        int discount = discountPolicy.discount(memberVIP, 10000);
        // THEN
        assertThat(discount).isEqualTo(0);
    }
}