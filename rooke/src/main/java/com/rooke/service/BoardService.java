package com.rooke.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.rooke.domain.PictureDTO;
import com.rooke.domain.RookeDTO;
import com.rooke.domain.SearchDto;
import com.rooke.paging.PagingResponse;

public interface BoardService {

  public boolean registerBoard(RookeDTO dto);

  public boolean registerBoard(RookeDTO dto, MultipartFile[] files);

  public RookeDTO getBoardDetail(Long idk);

  public boolean deleteBoard(Long idx);

  public PagingResponse<RookeDTO> getBoardList(SearchDto search);

  public List<PictureDTO> getPictureFileList(Long boardIdx);

  public PictureDTO getPictureDetail(Long idx);
}
