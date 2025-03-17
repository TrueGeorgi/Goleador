package goleador.backend.web;

import goleador.backend.web.dto.AuthenticationResponse;
import goleador.backend.web.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/1")
    public String test() {
        return "Yes! It worked";
    }
}
