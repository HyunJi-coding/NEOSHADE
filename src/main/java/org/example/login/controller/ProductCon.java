package org.example.login.controller;

import org.example.login.entity.Products;
import org.example.login.service.ProductsService;
import org.example.login.util.PriceFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductCon {
    @Autowired
    ProductsService productsService;

    @GetMapping("/list")
    public String productslist(Model model){
        List<Products> productsList = productsService.selectAll();

        model.addAttribute("productsList",productsList);
        return "/product/productlist";
    }

    @GetMapping("/{productId}")
    public String productDetail(@PathVariable Long productId, Model model){
        Products product = productsService.selectOne(productId);
        String formattedPrice = PriceFormatter.formatPrice(product.getPrice());
        model.addAttribute("formattedPrice", formattedPrice);
        model.addAttribute("product",product);
        return "/product/productview";
    }

}