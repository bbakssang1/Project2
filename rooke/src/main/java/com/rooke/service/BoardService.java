package com.rooke.service;

import java.util.List;
import com.rooke.domain.RookeDTO;

public interface BoardService {

  public boolean registerBoard(RookeDTO dto);

  public RookeDTO getBoardDetail(Long idk);

  public boolean deleteBoard(Long idx);

  public List<RookeDTO> getBoardList();
}
