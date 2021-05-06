package com.sjnono.domain.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BbsService {
    @Autowired
    private BoardRepository boardRepository;

    public Page<Board> findBoardByPageRequest(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return boardRepository.findAll(pageRequest);
    }
}
