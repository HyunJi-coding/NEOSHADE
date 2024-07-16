package org.example.login.controller;



import org.example.login.entity.Products;
import org.example.login.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

@Controller
public class CommCon {
    @Autowired
    ProductsService productsService;

    @GetMapping("/home")
    public String getProductListPage(Model model,
                               @RequestParam(defaultValue = "0") int pageNo,
                               @RequestParam(defaultValue = "8") int pageSize) {
        List<Products> productsList = productsService.findLatestProducts(pageNo, pageSize);
        model.addAttribute("productsList", productsList);
        return "/home/index";
    }

}
