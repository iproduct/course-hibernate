package course.hibernate.spring.web;

import course.hibernate.spring.dto.UserCreateDto;
import course.hibernate.spring.dto.UserDetailsDto;
import course.hibernate.spring.dto.UserUpdateDto;
import course.hibernate.spring.entity.User;
import course.hibernate.spring.exception.ClientEntityDataException;
import course.hibernate.spring.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<UserDetailsDto> getAllUsers() {
        return userService.findAll().stream().map(user -> mapper.map(user, UserDetailsDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDetailsDto getUserById(@PathVariable("id") Long id) {
        return mapper.map(userService.findById(id), UserDetailsDto.class);
    }

    @PostMapping
    public ResponseEntity<UserDetailsDto> create(@RequestBody UserCreateDto userDto) {
        User user = mapper.map(userDto, User.class);
        User created = userService.create(user);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(user.getId()))
                .body(mapper.map(created, UserDetailsDto.class));
    }

    @PutMapping("/{id}")
    public UserDetailsDto update(@PathVariable("id") Long id, @RequestBody UserUpdateDto userDto) {
        if(!id.equals(userDto.getId())) {
            throw new ClientEntityDataException(String.format("ID in URL:'%s' different from ID in body:'%s'.", id, userDto.getId()));
        }
        User user = mapper.map(userDto, User.class);
        return mapper.map(userService.update(user), UserDetailsDto.class);
    }

    @DeleteMapping("/{id}")
    public UserDetailsDto deleteById(@PathVariable("id") Long id) {
        return mapper.map(userService.deleteById(id), UserDetailsDto.class);
    }
}
