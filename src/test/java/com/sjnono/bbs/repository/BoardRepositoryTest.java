package com.sjnono.bbs.repository;

import com.sjnono.bbs.entity.Board;
import com.sjnono.bbs.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void di() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId("sbp1912");
//        userInfo.setName("박상범");
//        userInfoRepository.save(userInfo);
//
//        Board board = new Board();
//        board.setArticleTitle("테스트제목");
//        board.setArticleDetail("테스트디테일");
//        board.setUserInfo(userInfo);
//        boardRepository.save(board);
//
//        Optional<Board> optionalBoard = boardRepository.findById(board.getId());
//        Board findBoard = optionalBoard.get();
//
//        UserInfo findUserInfo = findBoard.getUserInfo();
//        List<Board> boardList = findUserInfo.getBoardList();


    }
}