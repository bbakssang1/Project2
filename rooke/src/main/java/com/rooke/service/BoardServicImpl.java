package com.rooke.service;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rooke.domain.RookeDTO;
import com.rooke.mapper.RookeMapper;

@Service
public class BoardServicImpl implements BoardService {


  @Autowired
  private RookeMapper rookeMapper;

  @Override
  public boolean registerBoard(RookeDTO dto) {
    int queryResult = 0;

    if (dto.getIdx() == null) {
      queryResult = rookeMapper.insertBoard(dto);
    } else {
      queryResult = rookeMapper.updateBoard(dto);
    }

    return (queryResult == 1) ? true : false;
  }

  @Override
  public RookeDTO getBoardDetail(Long idx) {
    return rookeMapper.selectBoardDetail(idx);
  }

  @Override
  public boolean deleteBoard(Long idx) {
    int queryResult = 0;
    RookeDTO rooke = rookeMapper.selectBoardDetail(idx);

    if (rooke != null && "N".equals(rooke.getDeleteYn())) {
      queryResult = rookeMapper.deleteBoard(idx);
    }
    return (queryResult == 1) ? true : false;
  }

  @Override
  public List<RookeDTO> getBoardList() {
    List<RookeDTO> boardList = Collections.emptyList();
    int boardTotalCount = rookeMapper.selectBoardTotalCount();

    if (boardTotalCount > 0) {
      boardList = rookeMapper.selectBoardList();
    }

    return boardList;
  }
}
