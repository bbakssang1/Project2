package com.rooke.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.rooke.domain.PictureDTO;

@Mapper
public interface PictureMapper {

  public int insertPicture(List<PictureDTO> pictureList);

  public PictureDTO selectPictureDetail(Long idx);

  public int deletePicture(Long boardIdx);

  public List<PictureDTO> selectPictureList(Long boardIdx);

  public int selectPictureTotalCount(Long boardIdx);
}
