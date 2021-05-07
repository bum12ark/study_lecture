package com.sjnono.domain.bbs;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sjnono.domain.bbs.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> findByTitle(String title) {
        return queryFactory
                .selectFrom(board)
                .where(board.title.eq(title))
                .fetch();
    }
}
