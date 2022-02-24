package course.hibernate.spring.web;

import course.hibernate.spring.dto.UserCreateDto;
import course.hibernate.spring.dto.UserDetailDto;
import course.hibernate.spring.dto.UserUpdateDto;
import course.hibernate.spring.entity.User;
import course.hibernate.spring.exception.InvalidClientDataException;
import course.hibernate.spring.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserDetailDto> getAllUsers() {
        return userService.findAll()
                .stream().map(user -> mapper.map(user, UserDetailDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDetailDto getUserById(@PathVariable("id") Long id) {
        return mapper.map(userService.findById(id), UserDetailDto.class);
    }

    @PostMapping
    public ResponseEntity<UserDetailDto> create(@RequestBody UserCreateDto userDto) {
        User user = mapper.map(userDto, User.class);
        User created = userService.create(user);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(user.getId()))
                .body(mapper.map(created, UserDetailDto.class));
    }

    @PutMapping("/{id}")
    public UserDetailDto update(@PathVariable("id") Long id, @RequestBody UserUpdateDto userDto) {
        if(!id.equals(userDto.getId())) {
            throw new InvalidClientDataException(String.format("ID in URL:'%s' different from ID in body:'%s'.", id, userDto.getId()));
        }
        User user = mapper.map(userDto, User.class);
        return mapper.map(userService.update(user), UserDetailDto.class);
    }

    @DeleteMapping("/{id}")
    public UserDetailDto deleteById(@PathVariable("id") Long id) {
        return mapper.map(userService.deleteById(id), UserDetailDto.class);
    }
}
