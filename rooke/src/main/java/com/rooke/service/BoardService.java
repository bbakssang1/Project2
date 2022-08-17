package com.rooke.service;

import com.rooke.domain.RookeDTO;
import com.rooke.domain.SearchDto;
import com.rooke.paging.PagingResponse;

public interface BoardService {

  public boolean registerBoard(RookeDTO dto);

  public RookeDTO getBoardDetail(Long idk);

  public boolean deleteBoard(Long idx);

  public PagingResponse<RookeDTO> getBoardList(SearchDto search);
}
