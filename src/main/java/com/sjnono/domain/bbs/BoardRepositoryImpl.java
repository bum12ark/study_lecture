package com.sjnono.domain.bbs;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import java.beans.Transient;
import java.util.List;

import static com.sjnono.domain.bbs.QBoard.board;
import static com.sjnono.domain.user.QUserInfo.userInfo;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> findJoinMember() {
        return queryFactory
                .selectFrom(board)
                .innerJoin(board.userInfo, userInfo)
                .fetchJoin()
                .fetch();
    }
}
