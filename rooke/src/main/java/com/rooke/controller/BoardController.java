package com.rooke.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.rooke.constant.Method;
import com.rooke.domain.PictureDTO;
import com.rooke.domain.RookeDTO;
import com.rooke.domain.SearchDto;
import com.rooke.paging.PagingResponse;
import com.rooke.service.BoardService;
import com.rooke.util.UiUtils;

@Controller
public class BoardController extends UiUtils {
  @Autowired
  private BoardService boardService;

  @GetMapping(value = "/board/write.do")
  public String openBoardWrite(@ModelAttribute RookeDTO dto,
      @RequestParam(value = "idx", required = false) Long idx, Model model) {
    if (idx == null) {
      model.addAttribute("rooke", new RookeDTO());
    } else {
      RookeDTO rooke = boardService.getBoardDetail(idx);
      if (rooke == null) {
        return "redirect:/board/list.do";
      }
      model.addAttribute("rooke", rooke);

      List<PictureDTO> fileList = boardService.getPictureFileList(idx);
      model.addAttribute("fileList", fileList);

    }


    return "board/write";
  }

  @PostMapping(value = "/board/register.do")
  public String registerBoard(final RookeDTO dto, MultipartFile[] files, Model model) {

    try {
      boolean isRegistered = boardService.registerBoard(dto, files);
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
      Model model, @ModelAttribute("dto") RookeDTO dto) {
    if (idx == null) {
      return showMessageWithRedirect("잘못된 접근입니다", "/board/list.do", Method.GET, null, model);
      // return "redirect:/board/list.do";
    }
    RookeDTO rooke = boardService.getBoardDetail(idx);
    if (rooke == null || "Y".equals(rooke.getDeleteYn())) {

      // return "redirect:/board/list.do";
      return showMessageWithRedirect("게시글이 없거나 이미 삭제된 글입니다", "/board/list.do", Method.GET, null,
          model);
    }
    model.addAttribute("rooke", rooke);

    List<PictureDTO> fileList = boardService.getPictureFileList(idx);
    model.addAttribute("fileList", fileList);

    return "board/view";

  }

  @PostMapping(value = "/board/delete.do")
  public String deleteBoard(@RequestParam(value = "idx", required = false) Long idx,
      @RequestParam final Map<String, Object> queryParams, Model model) {
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
    return showMessageWithRedirect("게시글이 삭제됐습니다!", "/board/list.do", Method.GET, queryParams,
        model);
  }


  @GetMapping("/board/download.do")
  public void downloadPictureFile(@RequestParam(value = "idx", required = false) final Long idx,
      Model model, HttpServletResponse response) {
    if (idx == null)
      throw new RuntimeException("잘못된 접근입니다");
    PictureDTO fileInfo = boardService.getPictureDetail(idx);
    if (fileInfo == null || "Y".equals(fileInfo.getDeleteYn())) {
      throw new RuntimeException("파일 정보를 찾을수 없습니다");
    }

    String uploadDate = fileInfo.getInsertTime().format(DateTimeFormatter.ofPattern("yyMMdd"));
    String uploadPath = Paths.get("C:", "bitjava", "upload", uploadDate).toString();
    String filename = fileInfo.getRealName();
    File file = new File(uploadPath, fileInfo.getSaveName());

    try {
      byte[] data = FileUtils.readFileToByteArray(file);
      response.setContentType("application/octet-stream");
      response.setContentLength(data.length);
      response.setHeader("Content-Transfer-Encoding", "binary");
      response.setHeader("Content-Disposition",
          "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");
      response.getOutputStream().write(data);
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (IOException e) {
      throw new RuntimeException("파일다운로드에 실패했습니다");

    } catch (Exception e) {

      throw new RuntimeException("시스템에 문제가 발생했습니다");
    }
  }
}
