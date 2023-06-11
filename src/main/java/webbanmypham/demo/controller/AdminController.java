package webbanmypham.demo.controller;
import webbanmypham.demo.enity.Category;
import webbanmypham.demo.enity.Product;
import webbanmypham.demo.enity.User;
import webbanmypham.demo.services.CategoryService;
import webbanmypham.demo.services.ProductService;
import webbanmypham.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    public String userDirectory = Paths.get("")
            .toAbsolutePath()
            .toString();
    String uploadsPath = userDirectory + File.separator + "ShoesStore" + File.separator + "images" + File.separator;
    @GetMapping
    public String adminPage()
    {
        return "admin/index";
    }

    @GetMapping("/products/table")
    public String listProduct(Model model){
        List<Product> products = productService.getAllBook();

        model.addAttribute("Products", products);
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/product-table";
    }
    @GetMapping("/products/table/add")
    public String addProductForm(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "product/add";
    }
    @PostMapping("/products/table/add")
    public String addBook(@Valid @ModelAttribute("product") Product Product, BindingResult bindingResult, Model model,@RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategory());
            return "product/add";
        }
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String originalFilename = file.getOriginalFilename();
                Path path = Paths.get(uploadsPath + originalFilename);
                if (!Files.exists(path)) {
                    Files.write(path, bytes);
                } else {
                }
                Product.setImage(originalFilename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload the file: " + e.getMessage());
            }
        }
        productService.addProduct(Product);
        return "redirect:/admin/products/table";
    }
    @GetMapping("/products/table/edit/{id}")
    public String editProductForm(@PathVariable("id") long id, Model model){
        Product editProduct = productService.getBookById(id);
        if(editProduct != null){
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAllCategory());
            return "product/edit";
        }else {
            return "error/404";
        }
    }
    @PostMapping("/products/table/edit")
    public String editProduct(@Valid @ModelAttribute("product") Product updateProduct, BindingResult bindingResult, Model model , @RequestParam("file") MultipartFile file){
        if (bindingResult.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategory());
            return "product/edit";
        }
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String originalFilename = file.getOriginalFilename();
                Path path = Paths.get(uploadsPath + originalFilename);
                if (!Files.exists(path)) {
                    Files.write(path, bytes);
                } else {
                }
                updateProduct.setImage( originalFilename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload the file: " + e.getMessage());
            }
        }
        productService.getAllBook().stream()
                .filter(book -> book.getId() == updateProduct.getId())
                .findFirst()
                .ifPresent(book -> {
                    productService.updateBook(updateProduct);
                });
        return "redirect:/admin/products/table";
    }
    @PostMapping("/products/table/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        productService.deleteBook(id);
        return "redirect:/admin/products/table";
    }
    @GetMapping("/category/table")
    public String listCategory(Model model){
        List<Category> category = categoryService.getAllCategory();
        model.addAttribute("categories", category);
        model.addAttribute("title", "category list");
        return "admin/category-table";
    }
    @GetMapping("/category/table/add")
    public String addBookForm(Model model){
        model.addAttribute("category", new Category());
        return "category/add";
    }
    @PostMapping("/category/table/add")
    public String addBook(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "category/add";
        }
        categoryService.addCategory(category);
        return "redirect:/admin/category/table";
    }
    @GetMapping("/category/table/edit/{id}")
    public String editBookForm(@PathVariable("id") long id, Model model){
        Category editCategory = categoryService.getCategoryById(id);
        if(editCategory != null){
            model.addAttribute("category", editCategory);
            return "category/edit";
        }else {
            return "not-found";
        }
    }
    @PostMapping("/category/table/edit")
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
        return "redirect:/admin/category/table";
    }
    @PostMapping("/category/table/delete/{id}")
    public String deleteCategory(@PathVariable("id") long id){
        categoryService.deleteCategory(id);
        return "redirect:/admin/category/table";
    }
    @GetMapping("/users/table")
    public String listUser(Model model){
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "admin/user-table";
    }
    @PostMapping("/users/table/delete/{id}")
    public String deleteUser(@PathVariable("id") long id){
        userService.deleteUser(id);
        return "redirect:/admin/users/table";
    }
}
