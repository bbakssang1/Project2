package com.rooke.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rooke.domain.RookeDTO;
import com.rooke.domain.SearchDto;
import com.rooke.paging.PagingResponse;
import com.rooke.service.BoardService;
import constant.Method;
import util.UiUtils;

@Controller
public class BoardController extends UiUtils {
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
  public String registerBoard(final RookeDTO dto, Model model) {
    try {
      boolean isRegistered = boardService.registerBoard(dto);
      if (isRegistered == false) {
        return showMessageWithRedirect("게시글 등록에 실패했습니다", "/board/list.do", Method.GET, null, model);
      }

    } catch (DataAccessException e) {
      return showMessageWithRedirect("데이터베이스 처리과정에서 문제 발생!", "/board/list.do", Method.GET, null,
          model);
    } catch (Exception e) {
      return showMessageWithRedirect("시스템에 문제 발생! 운영자에게 문의하세요", "/board/list.do", Method.GET, null,
          model);
    }

    return showMessageWithRedirect("게시글이 등록됐습니다!", "/board/list.do", Method.GET, null, model);
  }

  @GetMapping(value = "/board/list.do")
  public String openBoardList(@ModelAttribute("search") final SearchDto search, Model model) {
    PagingResponse<RookeDTO> boardList = boardService.getBoardList(search);
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

  @PostMapping(value = "/board/delete.do")
  public String deleteBoard(@RequestParam(value = "idx", required = false) Long idx, Model model) {
    if (idx == null) {
      return showMessageWithRedirect("잘못된 접근입니다", "/board/list.do", Method.GET, null, model);
    }
    try {
      boolean isDeleted = boardService.deleteBoard(idx);
      if (isDeleted == false) {
        return showMessageWithRedirect("게시글 삭제에 실패했습니다", "/board/list.do", Method.GET, null, model);
      }
    } catch (DataAccessException e) {
      return showMessageWithRedirect("데이터베이스 처리과정에서 문제 발생!", "/board/list.do", Method.GET, null,
          model);
    } catch (Exception e) {
      return showMessageWithRedirect("시스템에 문제 발생! 운영자에게 문의하세요!", "/board/list.do", Method.GET, null,
          model);
    }
    return showMessageWithRedirect("게시글이 삭제됐습니다!", "/board/list.do", Method.GET, null, model);
  }
}
