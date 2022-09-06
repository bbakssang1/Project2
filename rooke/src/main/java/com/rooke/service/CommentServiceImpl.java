package com.rooke.service;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rooke.domain.CommentDTO;
import com.rooke.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
  @Autowired
  private CommentMapper commentMapper;

  @Override
  public boolean registerComment(CommentDTO dto) {
    int queryResult = 0;

    if (dto.getIdx() == null) {
      queryResult = commentMapper.insertComment(dto);
    } else {
      queryResult = commentMapper.updateComment(dto);
    }
    return (queryResult == 1) ? true : false;
  }

  @Override
  public boolean deleteComment(Long idx) {
    int queryResult = 0;

    CommentDTO comment = commentMapper.commentDetail(idx);

    if (comment != null && "N".equals(comment.getDeleteYn())) {
      queryResult = commentMapper.deleteComment(idx);
    }
    return (queryResult == 1) ? true : false;
  }

  @Override
  public List<CommentDTO> getCommentList(CommentDTO dto) {
    List<CommentDTO> commentList = Collections.emptyList();

    int commentTotalCount = commentMapper.commentTotalCount(dto);
    if (commentTotalCount > 0) {
      commentList = commentMapper.commentList(dto);
    }
    return commentList;
  }
}
