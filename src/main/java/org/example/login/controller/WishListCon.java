package org.example.login.controller;

import org.example.login.dto.Request.WishListRequest;
import org.example.login.dto.Response.WishListResponse;
import org.example.login.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListCon {
    private final WishListService wishListService;

    @Autowired
    public WishListCon(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping
    public ResponseEntity<WishListResponse> addWishListItem(HttpServletRequest request, @Validated @RequestBody WishListRequest wishListRequest) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");
        WishListResponse response = wishListService.addWishList(userId, wishListRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WishListResponse>> getUserWishList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");
        List<WishListResponse> wishList = wishListService.getWishList(userId);
        return new ResponseEntity<>(wishList, HttpStatus.OK);
    }

    @DeleteMapping("/{wishListItemId}")
    public ResponseEntity<Void> removeWishListItem(HttpServletRequest request, @PathVariable Long wishListItemId) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");
        wishListService.removeWishList(userId, wishListItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}