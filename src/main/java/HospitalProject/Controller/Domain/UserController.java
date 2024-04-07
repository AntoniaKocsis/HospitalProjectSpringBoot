package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the password matches
            if (user.getPassword().equals(password)) {
                // Authentication successful
                // Here, you can create a session or issue a token to mark the user as logged in
                if (Objects.equals(user.getRole(), "admin"))
                    return "redirect:/index_hospitalStaff"; // Redirect admin to staff index
                return "redirect:/index_patients"; // Redirect users to the home page after successful login
            }
        }
        // Authentication failed, show error message
        model.addAttribute("error", "Invalid username or password");
        return "login"; // Show login form with error message
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate session to log out the user
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Clear any cookies or headers that prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.

        return "redirect:/"; // Redirect to the index page after logout
    }

}
