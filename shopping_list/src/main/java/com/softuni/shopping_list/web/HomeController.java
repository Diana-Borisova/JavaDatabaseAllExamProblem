package com.softuni.shopping_list.web;

import com.softuni.shopping_list.model.entity.CategoryEnum;
import com.softuni.shopping_list.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(HttpSession httpSession, Model model){
        if (httpSession.getAttribute("user") == null){
            return "index";
        }

        model.addAttribute("totalSum", productService.getTotalSum());
        model.addAttribute("drinks", productService.findAllByCategoryName(CategoryEnum.DRINK));
        model.addAttribute("foods", productService.findAllByCategoryName(CategoryEnum.FOOD));
        model.addAttribute("others", productService.findAllByCategoryName(CategoryEnum.OTHER));
        model.addAttribute("households", productService.findAllByCategoryName(CategoryEnum.HOUSEHOLD));

        return "home";

    }

}
