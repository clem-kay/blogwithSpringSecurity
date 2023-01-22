package com.example.blog.controllers;

import com.example.blog.exception.CannotSaveUserException;
import com.example.blog.model.User;
import com.example.blog.model.request.UserRequest;
import com.example.blog.model.response.Response;
import com.example.blog.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/list/{start}/{limit}")
    public ResponseEntity<Response> getUsers(@PathVariable("start") int start, @PathVariable("limit") int limit) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", userService.list(start, limit)))
                .message("propertyOwner Retrived")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }


    @PostMapping("/save")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserRequest request) {

        User user = userService.create(request);

        if (user == null){
            throw new CannotSaveUserException("Cannot save user");
        }else{
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(of("response", user))
                    .message("User created")
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .build()
            );
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getPropertyOwner(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", userService.get(id)))
                .message("user retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deletePropertyOwner(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", userService.delete(id)))
                .message("landlord deleted")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updatePropertyOwner(@RequestBody @Valid User request){
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", userService.update(request)))
                .message("landlord updated")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

}
