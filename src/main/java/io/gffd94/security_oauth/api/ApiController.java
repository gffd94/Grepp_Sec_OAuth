package io.gffd94.security_oauth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/user/test")
    public String userTest() {
        return "User Test!";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin Test!";
    }

}