package ru.geekbrains.vklimovich.springmvc.controller.mvc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.vklimovich.springmvc.auth.User;
import ru.geekbrains.vklimovich.springmvc.service.UserService;
import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String getUserList(Model model){
        List<User> users = userService.getUserList();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/form")
    public String userForm(@RequestParam(name="id", required = true) Long id, Model model){
        if(id == null){
            model.addAttribute("user", new User());
        }
        else{
            User user = userService.getUser(id);
            model.addAttribute("user", user);
        }
        return "user/form";
    }

    @PostMapping("/form")
    public RedirectView saveUser(@ModelAttribute User user,
                                 RedirectAttributes attributes) {
        userService.saveUser(user);
        return new RedirectView("");
    }
}
