package app.onlineschool.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        model.addAttribute("status", statusCode != null ? statusCode : 500);

        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = "An unexpected error occurred.";
        }
        model.addAttribute("message", errorMessage);

        return "error";
    }

//    public String getErrorPath() {
//        return "/error";
//    }
}