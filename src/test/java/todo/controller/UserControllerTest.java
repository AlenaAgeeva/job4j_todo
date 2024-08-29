package todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import todo.model.User;
import todo.service.SimpleUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {
    private SimpleUserService userService;
    private UserController userController;

    @BeforeEach
    public void initService() {
        userService = mock(SimpleUserService.class);
        userController = new UserController(userService);
    }

    @Test
    void whenStartThenGetLoginPage() {
        var viewName = userController.getLoginPage();
        assertThat(viewName).isEqualTo("users/login");
    }

    @Test
    void whenSignUpThenGetRegistrationPage() {
        var viewName = userController.getRegistrationPage();
        assertThat(viewName).isEqualTo("users/register");
    }

    @Test
    void whenLogOutThenGetLogout() {
        var session = mock(HttpSession.class);
        var request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
        var viewName = userController.logout(session);
        verify(session).invalidate();
        assertThat(viewName).isEqualTo("redirect:/users/login");
    }

    @Test
    void whenLoginUserThenSuccess() {
        var user = new User(0, "name", "email@example.com", "user", "123");
        var session = mock(HttpSession.class);
        var request = mock(HttpServletRequest.class);
        var model = new ConcurrentModel();
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);
        var viewName = userController.loginUser(user, model, request);
        verify(session).setAttribute("user", user);
        assertThat(viewName).isEqualTo("redirect:/");
    }

    @Test
    void whenLoginUserThenFailure() {
        var user = new User(0, "name", "email@example.com", "user", "123");
        var request = mock(HttpServletRequest.class);
        var model = new ConcurrentModel();
        when(userService.findByEmailAndPassword(any(), any()))
                .thenReturn(Optional.empty());
        var viewName = userController.loginUser(user, model, request);
        assertThat(viewName).isEqualTo("users/login");
        assertThat(model.getAttribute("message")).isEqualTo("Email or Password are incorrect.");
    }

    @Test
    void whenRegisterThenSuccess() {
        var user = new User(0, "name", "email@example.com", "user", "123");
        var model = new ConcurrentModel();
        when(userService.save(user)).thenReturn(Optional.of(user));
        var viewName = userController.register(user, model);
        assertThat(viewName).isEqualTo("redirect:/");
    }

    @Test
    void whenRegisterThenFailure() {
        var user = new User(0, "name", "email@example.com", "user", "123");
        var model = new ConcurrentModel();
        when(userService.save(user)).thenReturn(Optional.empty());
        var viewName = userController.register(user, model);
        assertThat(viewName).isEqualTo("errors/404");
        assertThat(model.getAttribute("message")).isEqualTo("User with this email does not exist.");
    }
}