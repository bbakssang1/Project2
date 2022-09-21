package com.rooke.service;

import java.util.Collections;
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
import com.rooke.util.FileUtility;

@Service
public class BoardServicImpl implements BoardService {


  @Autowired
  private RookeMapper rookeMapper;


  @Autowired
  private PictureMapper pictureMapper;


  @Autowired
  private FileUtility fileUtility;


  @Override
  public boolean registerBoard(RookeDTO dto) {
    int queryResult = 0;

    if (dto.getIdx() == null) {
      queryResult = rookeMapper.insertBoard(dto);
    } else {
      queryResult = rookeMapper.updateBoard(dto);
      // 파일이 추가,삭제,수정 된 경우
      if ("Y".equals(dto.getChangeYn())) {
        pictureMapper.deletePicture(dto.getIdx());

        // fileIdxs에 포함된 idx를 가지는 파일의 삭제여부를 'N'으로 업데이트
        if (CollectionUtils.isEmpty(dto.getFileIdxs()) == false) {
          pictureMapper.undeletePicture(dto.getFileIdxs());
        }
      }
    }



    // return (queryResult == 1) ? true : false;
    return (queryResult > 0);
  }


  @Override
  public boolean registerBoard(RookeDTO dto, MultipartFile[] files) {
    int queryResult = 1;
    if (registerBoard(dto) == false) {
      return false;
    }
    List<PictureDTO> fileList = fileUtility.uploadFiles(files, dto.getIdx());
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

  @Override
  public List<PictureDTO> getPictureFileList(Long boadIdx) {
    int fileTotalCount = pictureMapper.selectPictureTotalCount(boadIdx);
    if (fileTotalCount < 1) {
      return Collections.emptyList();
    }
    return pictureMapper.selectPictureList(boadIdx);
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
