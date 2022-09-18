package com.rooke.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureDTO {
  private Long idx;
  private Long boardIdx;
  private String realName;
  private String saveName;
  private long size;
  private String deleteYn;
  /** 등록일 */
  private LocalDateTime insertTime;

  /** 삭제일 */
  private LocalDateTime deleteTime;

}
