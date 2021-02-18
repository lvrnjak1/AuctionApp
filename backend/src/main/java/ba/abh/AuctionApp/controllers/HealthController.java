package ba.abh.AuctionApp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    @Secured("ROLE_BUYER")
    public ResponseEntity<String> checkApi(){
        return ResponseEntity.ok().body("Server says HI");
    }
}
