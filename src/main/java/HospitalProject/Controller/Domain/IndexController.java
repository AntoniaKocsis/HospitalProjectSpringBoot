package HospitalProject.Controller.Domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/doctors_index")
    public String showDoctors() {
        return "unlogged_view/doctors_index";
    }
    @GetMapping("/departments_index")
    public String showDepartments() {

        return "unlogged_view/departments_index";
    }
    @GetMapping("/facilities_index")
    public String showFacilities() {
        return "unlogged_view/facilities_index";
    }

    @GetMapping("/index_hospitalStaff")
    public String showHospitalStaffPage() {
        return "index_hospitalStaff"; // Assuming you have an HTML file named index_hospitalStaff.html
    }
    @GetMapping("/index_patients")
    public String showUserPage() {
        return "index_patients"; // Assuming you have an HTML file named index_hospitalStaff.html
    }
    @GetMapping("/contact")
    public String showContactPage(){
        return "unlogged_view/contact";

    }
}
