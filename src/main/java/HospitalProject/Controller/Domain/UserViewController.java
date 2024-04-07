package HospitalProject.Controller.Domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/doctors_index2")
    public String showDoctors() {
        return "user_view/doctors_index2";
    }
    @GetMapping("/departments_index2")
    public String showDepartments() {

        return "user_view/departments_index2";
    }
    @GetMapping("/facilities_index2")
    public String showFacilities() {
        return "user_view/facilities_index2";
    }

    @GetMapping("/contact2")
    public String showContactPage(){
        return "user_view/contact2";

    }
}
