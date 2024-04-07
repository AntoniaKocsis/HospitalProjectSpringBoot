package HospitalProject.Controller.Domain.HospitalServices.Prescriptions;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientService;
import HospitalProject.Controller.Domain.HospitalServices.Medications.Medication;
import HospitalProject.Controller.Domain.HospitalServices.Medications.MedicationService;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PrescriptionController {
    @Autowired private PrescriptionService service;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private MedicationService medicationService;

    @GetMapping("/prescriptions")
    public String showUserList(Model model) {
        List<Prescription> listPrescriptions = service.listAll();
        model.addAttribute("listPrescriptions", listPrescriptions);

        return "admin_view/prescription/prescriptions";
    }

    @GetMapping("/prescriptions/new")
    public String showNewForm(Model model) {
        List<Doctor> doctors = doctorService.listAll();
        List<Patient> patients = patientService.listAll();
        List<Medication> medicationsList = medicationService.listAll();
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("patients",patients);
        model.addAttribute("doctors", doctors);
        model.addAttribute("medicationsList",medicationsList);
        model.addAttribute("pageTitle", "Add New Prescription");
        return "admin_view/prescription/prescription_form";
    }

    @PostMapping("/prescriptions/save")
    public String saveUser(Prescription prescription, RedirectAttributes ra) {
        service.save(prescription);
        ra.addFlashAttribute("message", "The prescription has been saved successfully.");
        return "redirect:/prescriptions";
    }

    @GetMapping("/prescriptions/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            List<Medication> medicationsList = medicationService.listAll();
            Prescription prescription = service.get(id);
            model.addAttribute("prescription", prescription);
            model.addAttribute("medicationsList",medicationsList);
            model.addAttribute("pageTitle", "Edit Prescription (ID: " + id + ")");

            return "admin_view/prescription/prescription_edit_form";
        } catch (PrescriptionNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/prescriptions";
        }
    }

    @GetMapping("/prescriptions/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The prescription with ID " + id + " has been deleted.");
        } catch (PrescriptionNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/prescriptions";
    }
}
