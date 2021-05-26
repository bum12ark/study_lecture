package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        // GIVEN
        Member member = new Member();
        member.setName("park");

        // WHEN
        Long savedId = memberService.join(member);

        // THEN
        assertEquals(member, memberRepository.findOne(savedId)); // 1차 캐시에서 조회하기 때문에 같음
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // GIVEN
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // WHEN
        memberService.join(member1);

        // THEN
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

    }
}