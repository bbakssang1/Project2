package com.rooke.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rooke.domain.RookeDTO;
import com.rooke.service.BoardService;

@Controller
public class BoardController {
  @Autowired
  private BoardService boardService;

  @GetMapping(value = "/board/write.do")
  public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx,
      Model model) {
    if (idx == null) {
      model.addAttribute("rooke", new RookeDTO());
    } else {
      RookeDTO rooke = boardService.getBoardDetail(idx);
      if (rooke == null) {
        return "redirect:/board/list.do";
      }
      model.addAttribute("rooke", rooke);

    }


    return "board/write";
  }

  @PostMapping(value = "/board/register.do")
  public String registerBoard(final RookeDTO dto) {
    try {
      boolean isRegistered = boardService.registerBoard(dto);
      if (isRegistered == false) {
        // TODO 게시글 등록에 실패햇다는 메시지
      }

    } catch (DataAccessException e) {
      // TODO: 데이터베이스 처리과정 문제 메시지
    } catch (Exception e) {
      // TODO: 시스템 문제 발생 메시지
    }



    return "redirect:/board/list.do";
  }

  @GetMapping(value = "/board/list.do")
  public String openBoardList(Model model) {
    List<RookeDTO> boardList = boardService.getBoardList();
    model.addAttribute("boardList", boardList);

    return "board/list";
  }

  @GetMapping(value = "/board/view.do")
  public String openBoardDetail(@RequestParam(value = "idx", required = false) Long idx,
      Model model) {
    if (idx == null) {
      // todo=>잘못된 접근 메시지, 다시 게시글 리스트로
      return "redirect:/board/list.do";
    }
    RookeDTO rooke = boardService.getBoardDetail(idx);
    if (rooke == null || "Y".equals(rooke.getDeleteYn())) {
      // todo 존재치 않는 게시글이나 이미 삭제된 게시글이면 다시 게시글 리스트로
      return "redirect:/board/list.do";
    }
    model.addAttribute("rooke", rooke);
    return "board/view";

  }
}
