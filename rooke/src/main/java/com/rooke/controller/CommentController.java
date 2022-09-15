package com.rooke.controller;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rooke.adapter.GsonLocalDateTimeAdapter;
import com.rooke.domain.CommentDTO;
import com.rooke.service.CommentService;

@RestController
public class CommentController {
  @Autowired
  private CommentService commentService;

  @GetMapping(value = "/comments/{boardIdx}")
  public JsonObject getCommentList(@PathVariable("boardIdx") Long boardIdx,
      @ModelAttribute("dto") CommentDTO dto) {
    JsonObject jObj = new JsonObject();
    List<CommentDTO> commentList = commentService.getCommentList(dto);
    if (CollectionUtils.isEmpty(commentList) == false) {
      Gson gson = new GsonBuilder()
          .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
      JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray();
      jObj.add("commentList", jsonArr);
    }
    return jObj;
  }


  @RequestMapping(value = {"/comments", "/comments/{idx}"},
      method = {RequestMethod.POST, RequestMethod.PATCH})
  public JsonObject regeisterComment(@PathVariable(value = "idx", required = false) Long idx,
      @RequestBody final CommentDTO dto) {
    JsonObject jObj = new JsonObject();

    try {
      // if (idx != null) {
      // dto.setIdx(idx);
      // }
      boolean isRegistered = commentService.registerComment(dto);
      jObj.addProperty("result", isRegistered);


    } catch (DataAccessException e) {
      jObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생했습니다");
    } catch (Exception e) {
      jObj.addProperty("message", "시스템에 문제가 발생했습니다");
    }

    return jObj;
  }

  @DeleteMapping(value = "/comments/{idx}")
  public JsonObject deleteComment(@PathVariable("idx") final Long idx) {
    JsonObject jObj = new JsonObject();

    try {
      boolean isDeleted = commentService.deleteComment(idx);
      jObj.addProperty("result", isDeleted);
    } catch (DataAccessException e) {
      jObj.addProperty("message", "데이터베이스 처리중 문제 발생!");
    }

    catch (Exception e) {
      jObj.addProperty("message", "시스템에 문제 발생!");
    }
    return jObj;
  }

}
