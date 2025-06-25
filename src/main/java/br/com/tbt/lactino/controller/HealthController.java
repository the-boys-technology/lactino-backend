package br.com.tbt.lactino.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Void> healthHead() {
        return ResponseEntity.ok().build();
    }
}
