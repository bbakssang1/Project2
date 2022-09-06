package com.rooke.controller;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
}
