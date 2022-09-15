package com.rooke.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import com.rooke.domain.PictureDTO;
import com.rooke.domain.RookeDTO;
import com.rooke.domain.SearchDto;
import com.rooke.mapper.PictureMapper;
import com.rooke.mapper.RookeMapper;
import com.rooke.paging.Pagination;
import com.rooke.paging.PagingResponse;
import util.FileUtils;

@Service
public class BoardServicImpl implements BoardService {


  @Autowired
  private RookeMapper rookeMapper;

  @Autowired
  private PictureMapper pictureMapper;

  @Autowired
  private FileUtils fileUtils;

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
  public boolean registerBoard(RookeDTO dto, MultipartFile[] files) {
    int queryResult = 1;
    if (registerBoard(dto) == false) {
      return false;
    }
    List<PictureDTO> fileList = fileUtils.uploadFiles(files, dto.getIdx());
    if (CollectionUtils.isEmpty(fileList) == false) {
      queryResult = pictureMapper.insertPicture(fileList);
      if (queryResult < 1) {
        queryResult = 0;
      }
    }
    return (queryResult > 0);
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
  public PagingResponse<RookeDTO> getBoardList(final SearchDto search) {
    int boardTotalCount = rookeMapper.selectBoardTotalCount(search);
    Pagination pagination = new Pagination(boardTotalCount, search);
    search.setPagination(pagination);

    List<RookeDTO> list = rookeMapper.selectBoardList(search);
    return new PagingResponse<>(list, pagination);

  }
  // @Override
  // public List<RookeDTO> getBoardList(final SearchDto search) {
  // List<RookeDTO> boardList = Collections.emptyList();
  // int boardTotalCount = rookeMapper.selectBoardTotalCount(search);
  //
  // if (boardTotalCount > 0) {
  // boardList = rookeMapper.selectBoardList(search);
  // }
  //
  // return boardList;
  // }
}
