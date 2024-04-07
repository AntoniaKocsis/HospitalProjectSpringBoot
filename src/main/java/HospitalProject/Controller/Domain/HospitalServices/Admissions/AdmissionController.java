package HospitalProject.Controller.Domain.HospitalServices.Admissions;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientService;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomService;
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
public class AdmissionController {
    @Autowired
    private AdmissionService service;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private HospitalRoomService hospitalRoomService;

    @GetMapping("/admissions")
    public String showUserList(Model model) {
        List<Admission> listAdmissions = service.listAll();
        model.addAttribute("listAdmissions", listAdmissions);
        return "admin_view/admission/admissions";
    }

    @GetMapping("/admissions/new")
    public String showNewForm(Model model) {
        List<Doctor> doctors = doctorService.listAll();
        List<HospitalRoom> rooms = hospitalRoomService.listAvailableAdmissionRooms();
        List<Patient> patients = patientService.listAll();
        model.addAttribute("admission", new Admission());
        model.addAttribute("patients", patients);
        model.addAttribute("doctors", doctors);
        model.addAttribute("rooms", rooms);
        model.addAttribute("pageTitle", "Add New Admission");
        return "admin_view/admission/admission_form";
    }

    @PostMapping("/admissions/save")
    public String saveUser(Admission admission, RedirectAttributes ra) {
        service.save(admission);
        ra.addFlashAttribute("message", "The admission has been saved successfully.");
        return "redirect:/admissions";
    }

    @GetMapping("/admissions/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Admission admission = service.get(id);
            List<Doctor> doctors = doctorService.listAll();
            List<HospitalRoom> rooms = hospitalRoomService.listAvailableAdmissionRooms();
            List<Patient> patients = patientService.listAll();
            model.addAttribute("admission", admission);
            model.addAttribute("patients", patients);
            model.addAttribute("doctors", doctors);
            model.addAttribute("rooms", rooms);
            model.addAttribute("pageTitle", "Edit Admission (ID: " + id + ")");
            return "admin_view/admission/admission_edit_form";
        } catch (AdmissionNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admissions";
        }
    }

    @GetMapping("/admissions/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The admission ID " + id + " has been deleted.");
        } catch (AdmissionNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admissions";
    }
}

