package com.aftermath.backend.controller;

import com.aftermath.backend.dto.*;

import com.aftermath.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;

@RestController
@RequestMapping("/")
public class BasicController {
    public BasicController() {
    }
    @GetMapping("/")
    public ResponseEntity<BasicResponse> basicAPI(@Valid @RequestBody BasicRequest req) throws IOException {
        System.out.println("HelloHelloHelloHelloHelloHelloHelloHelloHelloHello");
        return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("yahoo"));
    }
}

