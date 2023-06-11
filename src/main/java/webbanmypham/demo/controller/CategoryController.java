package webbanmypham.demo.controller;

import webbanmypham.demo.enity.Category;
import webbanmypham.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategory(Model model){
        List<Category> category = categoryService.getAllCategory();
        model.addAttribute("categories", category);
        model.addAttribute("title", "category list");
        return "category/list";
    }
    @GetMapping("/add")
    public String addBookForm(Model model){
        model.addAttribute("category", new Category());
        return "category/add";
    }
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "category/add";
        }
        categoryService.addCategory(category);
        return "redirect:/category";
    }
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") long id, Model model){
        Category editCategory = categoryService.getCategoryById(id);
        if(editCategory != null){
            model.addAttribute("category", editCategory);
            return "category/edit";
        }else {
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("category")Category updateCategory, BindingResult bindingResult, Model model ){
        if (bindingResult.hasErrors()){
            return "category/edit";
        }
        categoryService.getAllCategory().stream()
                .filter(category -> category.getId() == updateCategory.getId())
                .findFirst()
                .ifPresent(book -> {
                    categoryService.updateCategory(updateCategory);
                });
        return "redirect:/category";
    }
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id){
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }
}
