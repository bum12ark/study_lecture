package com.sjnono.domain.bbs;

import com.sjnono.domain.user.UserInfo;
import com.sjnono.domain.user.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
class BbsServiceTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Test @Transactional
    public void 양방향매핑_인서트확인() {
        // given
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("bum12ark@gmail.com");
        userInfo.setName("박상범");
        userInfo.setPassword("1234");

        userInfoRepository.save(userInfo);

        String title = "제목";
        String content = "안녕하세요. 내용입니다.";
        Board board = new Board(title, content);

        board.setUserInfo(userInfo);  // 중요!

        // userInfo.getBoardList().add(board);

        boardRepository.save(board);
    }

    @Test
    public void querydsl_조인_검색() {
        List<Board> joinMember = boardRepository.findJoinMember();

        assertThat(joinMember.get(0).getUserInfo().getName(), is(notNullValue()));
    }
}