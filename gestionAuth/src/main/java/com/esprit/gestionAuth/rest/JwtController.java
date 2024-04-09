package com.esprit.gestionAuth.rest;

import com.esprit.gestionAuth.persistence.entity.JwtRequest;
import com.esprit.gestionAuth.persistence.entity.JwtResponse;
import com.esprit.gestionAuth.services.Implementation.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}
