package ru.geekbrains.vklimovich.springmvc.controller.mvc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.vklimovich.springmvc.service.CartService;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/add")
    public RedirectView addProductToCart(@RequestParam(name="id", required = true) Long id,
                                         RedirectAttributes attributes){
        cartService.addProduct(id, 1);
        return new RedirectView("../../product");
    }
}
