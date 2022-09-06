package com.rooke.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.rooke.domain.CommentDTO;

@Mapper
public interface CommentMapper {
  public int insertComment(CommentDTO dto);

  public CommentDTO commentDetail(Long idx);

  public int updateComment(CommentDTO dto);

  public int deleteComment(Long idx);

  public List<CommentDTO> commentList(CommentDTO dto);

  public int commentTotalCount(CommentDTO dto);
}
