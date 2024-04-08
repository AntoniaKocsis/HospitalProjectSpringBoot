package HospitalProject.Controller.Domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/submitContactForm")
    public String submitContactForm(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message) {

        // Create a SimpleMailMessage instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("antoniakocsis66@gmail.com"); // Change this to your email address
        mailMessage.setSubject("New message from " + name);
        mailMessage.setText("Sender's Name: " + name + "\nSender's Email: " + email + "\n\nMessage:\n" + message);
        // Send email
        emailSender.send(mailMessage);

        // Redirect back to the contact page
        return "redirect:/contact";
    }
}

