package course.hibernate.spring.web;

import course.hibernate.spring.dto.UserDetailsDto;
import course.hibernate.spring.entity.User;
import course.hibernate.spring.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
