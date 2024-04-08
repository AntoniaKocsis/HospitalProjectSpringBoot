package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.HospitalConfiguration.Department.Department;
import HospitalProject.Controller.Domain.HospitalConfiguration.Department.DepartmentService;
import HospitalProject.Controller.Domain.Patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
public class AppointmentScheduling {
    @Autowired
    private DepartmentService departmentService;

    private Patient patient;
    private Department department;
    private List<Doctor> doctors;

    @GetMapping("/personal_information")
    public String showNewForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "personal_information";
    }

    @PostMapping("/personal_information/save")
    public String savePatient(Patient patient, RedirectAttributes ra) {
        this.patient = patient;
        ra.addFlashAttribute("message", "The patient has been saved successfully.");
        return "redirect:/select_department";
    }

    @GetMapping("/select_department")
    public String selectDepartment() {
        return "select_department";
    }

    @GetMapping("/select_doctor")
    public String selectDoctor(@RequestParam("selectedDepartment") String selectedDepartment, Model model) {
        // Use the selectedDepartment value as needed
        System.out.println("Selected department: " + selectedDepartment);
        // Add any additional processing logic here
        for (Department department1 : departmentService.listAll()) {
            if (Objects.equals(department1.getName(), selectedDepartment)) {
                department = department1;
                break;
            }
        }
        if (department != null) {
            doctors = department.getDoctors();
        }
        model.addAttribute("doctors", doctors);
        // Return the view where you want to display the doctors for the selected department
        return "select_doctor"; // Replace "select_doctor" with your view name
    }
}
