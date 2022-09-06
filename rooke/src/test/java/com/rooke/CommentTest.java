package com.rooke;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.rooke.domain.CommentDTO;
import com.rooke.service.CommentService;

@SpringBootTest
public class CommentTest {
  @Autowired
  private CommentService commentService;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void registerComment() {
    int number = 20;

    for (int i = 1; i <= 20; i++) {
      CommentDTO dto = new CommentDTO();
      dto.setBoardIdx((long) 256);
      dto.setContent(i + "번 댓글을 추가합니다");
      dto.setWriter(i + "번 회원");
      commentService.registerComment(dto);
    }
    logger.debug("댓글" + number + "개가 등록됐습니다");
  }

  @Test
  public void deleteComment() {
    commentService.deleteComment((long) 10);
    getCommentList();
  }

  @Test
  public void getCommentList() {
    CommentDTO dto = new CommentDTO();
    dto.setBoardIdx((long) 256);
    commentService.getCommentList(dto);
  }
}
