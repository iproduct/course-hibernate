package course.hibernate.spring.web;

import course.hibernate.spring.entity.User;
import course.hibernate.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping
    List<User> getByUsers() {
        return userService.findAll();
    }
}
