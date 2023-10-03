package med.voll.api.controller;

import med.voll.api.domain.users.User;
import med.voll.api.domain.users.UserData;
import med.voll.api.infra.security.TokenData;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @PostMapping
    public ResponseEntity login(@RequestBody UserData userData){
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userData.username(), userData.password());

        var authUser = authenticationManager.authenticate(authToken);

        var JWTtoken = tokenService.generateToken((User) authUser.getPrincipal());
        return ResponseEntity.ok(new TokenData(JWTtoken));
    }
}
