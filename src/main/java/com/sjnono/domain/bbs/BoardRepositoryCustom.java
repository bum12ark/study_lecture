package com.sjnono.domain.bbs;

import java.util.List;

public interface BoardRepositoryCustom {
    /**
     * 게시판 조회를 위한 SELECT 쿼리
     * @return
     */
    List<Board> findJoinMember();
}
