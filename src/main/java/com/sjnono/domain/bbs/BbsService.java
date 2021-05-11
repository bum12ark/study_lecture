package com.sjnono.domain.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BbsService {
    private final BoardRepository boardRepository;

    public BbsService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> findJoinMember() {
        return boardRepository.findJoinMember();
    }
}
