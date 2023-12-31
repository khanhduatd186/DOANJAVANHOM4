package webbanmypham.demo.controller;

import webbanmypham.demo.enity.Product;
import webbanmypham.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public String home(Model model)
    {
        List<Product> products = productService.getAllBook().stream().limit(8).collect(Collectors.toList());;
        model.addAttribute("Products", products);
        return "home/index";
    }

}
