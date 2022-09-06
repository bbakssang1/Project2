package com.rooke.service;

import java.util.List;
import com.rooke.domain.CommentDTO;

public interface CommentService {
  public boolean registerComment(CommentDTO dto);

  public boolean deleteComment(Long idx);

  public List<CommentDTO> getCommentList(CommentDTO dto);
}
