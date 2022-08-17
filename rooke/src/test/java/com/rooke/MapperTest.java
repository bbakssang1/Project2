package com.rooke;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rooke.domain.RookeDTO;
import com.rooke.domain.SearchDto;
import com.rooke.mapper.RookeMapper;

@SpringBootTest
public class MapperTest {
  @Autowired
  private RookeMapper rookeMapper;

  // 게시글 1개 삽입 테스트
  @Test
  public void testInsert() {
    RookeDTO dto = new RookeDTO();
    dto.setTitle("첫번째 게시글");
    dto.setContent("첫번째 내용");
    dto.setWriter("운영자");

    int result = rookeMapper.insertBoard(dto);
    System.out.println("결과는 " + result + "입니다.");
  }

  // 게시글 여러개 삽입 테스트
  @Test
  public void testManyInsert() {
    for (int i = 2; i <= 10; i++) {
      RookeDTO dto = new RookeDTO();
      dto.setTitle(i + "번째 게시글 제목");
      dto.setContent(i + "번째 게시글 내용");
      dto.setWriter(i + "번째 글쓴이");
      rookeMapper.insertBoard(dto);
    }
  }

  // 게시글 디테일 확인 테스트
  @Test
  public void testDetail() {
    RookeDTO detai = rookeMapper.selectBoardDetail((long) 1);
    try {
      String json =
          new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(detai);

      System.out.println(json);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // 게시글 업데이트
  @Test
  public void testUpdate() {
    RookeDTO dto = new RookeDTO();
    dto.setTitle("1번 수정");
    dto.setContent("1번 글 내용 수정");
    dto.setWriter("운영자 친구");
    dto.setIdx((long) 1);

    int result = rookeMapper.updateBoard(dto);
    if (result == 1) {
      RookeDTO detai = rookeMapper.selectBoardDetail((long) 1);
      try {
        String json =
            new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(detai);
        System.out.println(json);

      } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  // 삭제
  @Test
  public void testDelete() {
    int result = rookeMapper.deleteBoard((long) 1);
    if (result == 1) {
      RookeDTO detai = rookeMapper.selectBoardDetail((long) 1);
      try {
        String json =
            new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(detai);
        System.out.println(json);

      } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testSelectList(final SearchDto search) {
    int boardCount = rookeMapper.selectBoardTotalCount(search);
    if (boardCount > 0) {
      List<RookeDTO> rookeList = rookeMapper.selectBoardList(search);
      if (CollectionUtils.isEmpty(rookeList) == false) {
        for (RookeDTO dto : rookeList) {
          System.out.println("-------------------------------");
          System.out.println(dto.getTitle());
          System.out.println(dto.getContent());
          System.out.println(dto.getWriter());
          System.out.println("-------------------------------");
        }

      }
    }
  }
}
