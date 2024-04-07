package HospitalProject.Controller.Domain.Singleton;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.NoAvailableAdmissionRoomException;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Doctor.DoctorNotFoundException;
import HospitalProject.Controller.Domain.Doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class EmergencyRoomController {

    @Autowired private EmergencyRoomService service;
    @Autowired private DoctorService doctorService;
    @GetMapping("/emergencyRoom_doctors")
    public String showOnCallDoctors(Model model) {
        List<Doctor> listDoctors = service.listOnCallDoctors();
        model.addAttribute("listDoctors", listDoctors);

        return "admin_view/emergencyRoom/emergencyRoom_doctors";
    }

    @GetMapping("/emergencyRoom")
    public String showWaitingList(Model model) {
        Map<Patient,String> waitingList = service.listWaitingList();
        model.addAttribute("waitingList", waitingList);
        return "admin_view/emergencyRoom/emergencyRoom";
    }

    @GetMapping("/emergencyRoom/new")
    public String showNewForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("pageTitle", "Add New Patient");
        return "admin_view/emergencyRoom/emergencyRoom_form";
    }
    @PostMapping("/emergencyRoom/save")
    public String savePatient(@RequestParam String condition, Patient patient, RedirectAttributes ra) {
        service.addToWaitingList(patient,condition);
        ra.addFlashAttribute("message", "The patient has been saved successfully.");
        return "redirect:/emergencyRoom";
    }
    @GetMapping("/emergencyRoom_doctors/new")
    public String addOnCallDoctor(Model model) {
        List<Doctor> doctors = doctorService.listOffCall();
        model.addAttribute("doctors", doctors);
        model.addAttribute("pageTitle", "Add New Doctor");
        return "admin_view/emergencyRoom/emergencyRoom_doctor_form";
    }
    @PostMapping("/emergencyRoom_doctors/save")
    public String saveDoctor(@RequestParam("selectedDoctor") Integer selectedDoctorId, RedirectAttributes ra) throws DoctorNotFoundException {
        Doctor selectedDoctor = doctorService.get(selectedDoctorId);
        service.addOnCallDoctor(selectedDoctor);
        ra.addFlashAttribute("message", "The doctor is now on call.");
        return "redirect:/emergencyRoom_doctors";
    }

    @GetMapping("/emergencyRoom_doctors/delete/{id}")
    public String removeOnCallDoctor(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.removeOnCallDoctor(id);
            ra.addFlashAttribute("message", "The doctor with ID " + id + " has been deleted.");
        } catch (DoctorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/emergencyRoom_doctors";
    }
    @GetMapping("/emergencyRoom/delete/{id}")
    public String removeFromWaitingList(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.removeFromWaitingList(id);
            ra.addFlashAttribute("message", "The patient with ID " + id + " has been deleted.");
        } catch (PatientNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/emergencyRoom";
    }
    @GetMapping("/emergencyRoom/handleNextPatient")
    public String handleNextPatient(Model model) {
        try {
            boolean patientHandled = service.handleNextPatient();
            if (patientHandled) {
                model.addAttribute("message", "Next patient handled successfully.");
            } else {
                model.addAttribute("message", "No patients in the waiting list.");
            }

        } catch (NoAvailableAdmissionRoomException e) {
            model.addAttribute("message", "No available admission rooms.");
        }
        // Redirect to the emergencyRoom page after handling the patient
        return "redirect:/emergencyRoom";
    }

}
