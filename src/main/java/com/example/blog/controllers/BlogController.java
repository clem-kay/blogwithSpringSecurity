package com.example.blog.controllers;


import com.example.blog.model.Blog;
import com.example.blog.model.response.Response;

import com.example.blog.service.impl.BlogServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogServiceImpl blogService;

    @GetMapping("/list/{start}/{limit}")
    public ResponseEntity<Response> getBlog(@PathVariable("start") int start, @PathVariable("limit") int limit) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", blogService.list(start, limit)))
                .message("blog Retrived")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }


    @PostMapping("/save")
    public ResponseEntity<Response> saveBlog(@RequestBody @Valid Blog request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", blogService.create(request, authorization)))
                .message("blog created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getSingleBlog(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", blogService.get(id)))
                .message("blog retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBlog(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", blogService.delete(id)))
                .message("blog deleted")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updatePropertyOwner(@RequestBody @Valid Blog request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", blogService.update(request, authorization)))
                .message("blog updated")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }


}
