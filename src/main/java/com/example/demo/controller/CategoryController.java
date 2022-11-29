package com.example.demo.controller;

import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Category;
import com.example.demo.model.User;
import com.example.demo.security.userprincal.UserDetailService;
import com.example.demo.service.IUserService;
import com.example.demo.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/cate")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IUserService userService;
    @Autowired
    private UserDetailService userDetailsService;

    @GetMapping
    public ResponseEntity<?> getList() {
        Iterable<Category> listCate = categoryService.findAll();
        return new ResponseEntity<>(listCate, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCate(@RequestBody Category category) {
        User user = userDetailsService.getCurrentUser();
        if (user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponMessage("no_login"),HttpStatus.OK);
        }
        if (categoryService.existsByName(category.getName())){
            return new ResponseEntity<>(new ResponMessage("category_invalid"),HttpStatus.OK);
        }
        if (category.getAvatar().trim().equals("")){
            return new ResponseEntity<>(new ResponMessage("avatar_not"), HttpStatus.NOT_FOUND);
        }
        categoryService.save(category);
        return new ResponseEntity<>(new ResponMessage("thêm thành công"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailCate(@PathVariable("id") Category category) {
        return category == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCate(@PathVariable Long id, @RequestBody Category category) {
        Category cate1 = categoryService.findById(id);
        cate1.setName(category.getName());
        categoryService.save(cate1);
        return new ResponseEntity<>(new ResponMessage("sửa thành công"), HttpStatus.OK);
    }


}
