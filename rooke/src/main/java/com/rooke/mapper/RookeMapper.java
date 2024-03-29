package com.rooke.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.rooke.domain.RookeDTO;
import com.rooke.domain.SearchDto;

@Mapper
public interface RookeMapper {
  public int insertBoard(RookeDTO dto);

  public RookeDTO selectBoardDetail(Long idx);

  public int updateBoard(RookeDTO dto);

  public int deleteBoard(Long idx);

  public List<RookeDTO> selectBoardList(SearchDto search);

  public int selectBoardTotalCount(SearchDto search);
}
