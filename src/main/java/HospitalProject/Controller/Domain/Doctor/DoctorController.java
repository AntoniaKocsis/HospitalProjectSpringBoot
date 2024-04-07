package HospitalProject.Controller.Domain.Doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DoctorController {
    @Autowired private DoctorService service;

    @GetMapping("/doctors")
    public String showList(Model model) {
        List<Doctor> listDoctors = service.listAll();
        model.addAttribute("listDoctors", listDoctors);

        return "admin_view/doctor/doctors";
    }

    @GetMapping("/doctors/new")
    public String showNewForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("pageTitle", "Add New Doctor");
        return "admin_view/doctor/doctor_form";
    }

    @PostMapping("/doctors/save")
    public String save(Doctor doctor, RedirectAttributes ra) {
        service.save(doctor);
        ra.addFlashAttribute("message", "The doctor has been saved successfully.");
        return "redirect:/doctors";
    }

    @GetMapping("/doctors/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Doctor doctor = service.get(id);
            model.addAttribute("doctor", doctor);
            model.addAttribute("pageTitle", "Edit Doctor (ID: " + id + ")");

            return "admin_view/doctor/doctor_form";
        } catch (DoctorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/doctors";
        }
    }

    @GetMapping("/doctors/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The doctor with ID " + id + " has been deleted.");
        } catch (DoctorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/doctors";
    }
}
