package com.jack.dao;

import com.jack.entity.Game;
import java.util.List;

public interface GameMapper {
    int deleteByPrimaryKey(Integer gameId);

    int insert(Game record);

    Game selectByPrimaryKey(Integer gameId);

    List<Game> selectAll();

    int updateByPrimaryKey(Game record);
}