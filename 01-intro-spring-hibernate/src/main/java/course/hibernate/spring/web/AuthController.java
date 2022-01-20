package course.hibernate.spring.web;

import course.hibernate.spring.dto.Credentials;
import course.hibernate.spring.dto.LoginResponse;
import course.hibernate.spring.dto.UserSummaryDto;
import course.hibernate.spring.entity.Role;
import course.hibernate.spring.entity.User;
import course.hibernate.spring.exception.ClientEntityDataException;
import course.hibernate.spring.service.UserService;
import course.hibernate.spring.util.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ModelMapper mapper;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                          ModelMapper mapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.mapper = mapper;
    }

    @PostMapping("register")
    public ResponseEntity<User> findByTitle(@Valid @RequestBody User user, Errors errors){
        if (errors.hasErrors()) {
            throw new ClientEntityDataException("Invalid user data",
                    errors.getAllErrors().stream().map(e -> e.toString()).collect(Collectors.toList()));
        }
        if(!user.getRoles().equals(Set.of(Role.READER))){
            throw new ClientEntityDataException(
                String.format("Role: '%s' is invalid.You can self-register only in 'READER' role", user.getRoles()));
        }
        User created = userService.create(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PostMapping("login")
    public LoginResponse login(@Valid @RequestBody Credentials credentials, Errors errors) {
        if (errors.hasErrors()) {
            throw new ClientEntityDataException("Invalid user data",
                    errors.getAllErrors().stream().map(e -> e.toString()).collect(Collectors.toList()));
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()
        ));
        final User user = userService.findByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        return new LoginResponse(mapper.map(user, UserSummaryDto.class), token);
    }
}












