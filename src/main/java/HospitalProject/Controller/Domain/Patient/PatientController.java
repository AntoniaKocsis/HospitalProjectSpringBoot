package HospitalProject.Controller.Domain.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PatientController {
    @Autowired private PatientService service;

    @GetMapping("/patients")
    public String showPatientsList(Model model) {
        List<Patient> listPatients = service.listAll();
        model.addAttribute("listPatients", listPatients);

        return "admin_view/patient/patients";
    }

    @GetMapping("/patients/new")
    public String showNewForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("pageTitle", "Add New Patient");
        return "admin_view/patient/patient_form";
    }

    @PostMapping("/patients/save")
    public String savePatient(Patient patient, RedirectAttributes ra) {
        service.save(patient);
        ra.addFlashAttribute("message", "The patient has been saved successfully.");
        return "redirect:/patients";
    }

    @GetMapping("/patients/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Patient user = service.get(id);
            model.addAttribute("patient", user);
            model.addAttribute("pageTitle", "Edit Patient (ID: " + id + ")");

            return "admin_view/patient/patient_form";
        } catch (PatientNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/patients";
        }
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The patient ID " + id + " has been deleted.");
        } catch (PatientNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/patients";
    }
}
