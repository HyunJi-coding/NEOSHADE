package org.example.login.controller;

import org.example.login.entity.Products;
import org.example.login.entity.Reviews;
import org.example.login.service.ProductsService;
import org.example.login.service.ReviewsService;
import org.example.login.util.PriceFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Autowired
    ReviewsService reviewsService;

    @GetMapping("/list")
    public String productslist(Model model){
        List<Products> productsList = productsService.selectAll();

        model.addAttribute("productsList",productsList);
        return "/product/productlist";
    }

//    @GetMapping("/{productId}")
//    public String productDetail(@PathVariable Long productId, Model model){
//        Products product = productsService.selectOne(productId);
//        String formattedPrice = PriceFormatter.formatPrice(product.getPrice());
//        model.addAttribute("formattedPrice", formattedPrice);
//        model.addAttribute("product",product);
//        return "/product/productview";
//    }

    @GetMapping("/{productId}")
    public String productDetail(@PathVariable Long productId,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "3") int size,
                                Model model) {
        // 제품 정보 가져오기
        Products product = productsService.selectOne(productId);
        String formattedPrice = PriceFormatter.formatPrice(product.getPrice());

        // 리뷰 페이지네이션 가져오기
        Page<Reviews> reviewPage = reviewsService.getReviewsByProductId(productId, page, size);

        // 모델에 데이터 추가
        model.addAttribute("product", product);
        model.addAttribute("formattedPrice", formattedPrice);
        model.addAttribute("reviewList", reviewPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reviewPage.getTotalPages());
        model.addAttribute("productId", productId);

        return "/product/productview";
    }

}