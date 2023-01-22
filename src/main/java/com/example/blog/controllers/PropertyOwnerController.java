package com.example.blog.controllers;


import com.example.blog.model.PropertyOwner;
import com.example.blog.model.request.PropertyOwnerRequest;
import com.example.blog.service.impl.PropertyOwnerServiceImpl;
import com.example.blog.model.response.Response;
import static java.util.Map.*;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/propertyOwner")
public class PropertyOwnerController {

    private final PropertyOwnerServiceImpl propertyOwnerService;

    @GetMapping("/list/{start}/{limit}")
    public ResponseEntity<Response> getServers(@PathVariable("start") int start, @PathVariable("limit") int limit) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", propertyOwnerService.list(start, limit)))
                .message("propertyOwner Retrived")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }


    @PostMapping("/save")
    public ResponseEntity<Response> savePropertyOwner(@RequestBody @Valid PropertyOwnerRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", propertyOwnerService.create(request,authorization)))
                .message("LandLord created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getPropertyOwner(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", propertyOwnerService.get(id)))
                .message("Lanlord retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deletePropertyOwner(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", propertyOwnerService.delete(id)))
                .message("landlord deleted")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updatePropertyOwner(@RequestBody @Valid PropertyOwner request){
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("response", propertyOwnerService.update(request)))
                .message("landlord updated")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }


}
