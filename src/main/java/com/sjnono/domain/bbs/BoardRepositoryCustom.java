package com.sjnono.domain.bbs;

import java.util.List;

public interface BoardRepositoryCustom {
    List<Board> findByTitle(String title);
}
