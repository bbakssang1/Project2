package com.rooke.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {

  @GetMapping(value = "/message")
  @ResponseBody // public @ResponseBody String testByResponseBody()와 같이 이턴 타입 좌측에 지정 가능
  public String testByResponseBody() {
    String message = "안녕하세요 난 뭘까요?";
    return message;
  }

  @GetMapping(value = "/members")

  public Map<Integer, Object> testByResponseBody2() {
    Map<Integer, Object> members = new HashMap<>();

    for (int i = 1; i <= 10; i++) {
      Map<String, Object> member = new HashMap<>();
      member.put("idx", i);
      member.put("nickname", "상준" + i);
      member.put("height", i + 20);
      member.put("weight", i + 30);
      members.put(i, member);
    }
    return members;
  }


}
