package com.rooke.util;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import com.rooke.constant.Method;

@Controller
public class UiUtils {
  public String showMessageWithRedirect(
      @RequestParam(value = "message", required = false) String message,
      @RequestParam(value = "redirectUri", required = false) String redirectUri,
      @RequestParam(value = "method", required = false) Method method,
      @RequestParam(value = "dto", required = false) Map<String, Object> dto, Model model) {
    model.addAttribute("message", message);
    model.addAttribute("redirectUri", redirectUri);
    model.addAttribute("method", method);
    model.addAttribute("dto", dto);

    return "utils/message-redirect";
  }
}
