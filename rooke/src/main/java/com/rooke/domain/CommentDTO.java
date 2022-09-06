package com.rooke.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
  private Long idx;
  private Long boardIdx;
  private String content;
  private String writer;
  private String deleteYn;
  /** 등록일 */
  private LocalDateTime insertTime;

  /** 수정일 */
  private LocalDateTime updateTime;

  /** 삭제일 */
  private LocalDateTime deleteTime;
}
