package com.fpoly.api;

import com.fpoly.dto.request.BlogRequest;
import com.fpoly.dto.response.BlogResponse;
import com.fpoly.servive.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin
public class BlogApi {

    @Autowired
    private BlogService blogService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> save(@RequestBody BlogRequest blogRequest){
        BlogResponse result = blogService.save(blogRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/admin/update")
    public ResponseEntity<?> update(@RequestBody BlogRequest blogRequest){
        BlogResponse result = blogService.save(blogRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        blogService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/findAll")
    public ResponseEntity<?> findAll(Pageable pageable){
        Page<BlogResponse> result = blogService.findAll(pageable);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/public/findById")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        BlogResponse result = blogService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/public/findPrimaryBlog")
    public ResponseEntity<?> findPrimaryCategory(){
        BlogResponse result = blogService.blogPrimary();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}