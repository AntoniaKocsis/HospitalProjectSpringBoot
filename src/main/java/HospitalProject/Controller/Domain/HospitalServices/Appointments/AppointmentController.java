package HospitalProject.Controller.Domain.HospitalServices.Appointments;

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
public class AppointmentController {
    @Autowired
    private AppointmentService service;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private HospitalRoomService hospitalRoomService;

    @GetMapping("/appointments")
    public String showUserList(Model model) {
        List<Appointment> listAppointments = service.listAll();
        model.addAttribute("listAppointments", listAppointments);
        return "admin_view/appointment/appointments";
    }

    @GetMapping("/appointments/new")
    public String showNewForm(Model model) {
        List<Doctor> doctors = doctorService.listAll();
        List<HospitalRoom> rooms = hospitalRoomService.listAllExaminationRooms();
        List<Patient> patients = patientService.listAll();
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients",patients);
        model.addAttribute("doctors", doctors);
        model.addAttribute("rooms", rooms);
        model.addAttribute("pageTitle", "Add New Appointment");
        return "admin_view/appointment/appointment_form";
    }

    @PostMapping("/appointments/save")
    public String saveUser(Appointment appointment, RedirectAttributes ra) {
        service.save(appointment);
        ra.addFlashAttribute("message", "The appointment has been saved successfully.");
        return "redirect:/appointments";
    }

    @GetMapping("/appointments/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Appointment appointment = service.get(id);
            List<Doctor> doctors = doctorService.listAll();
            List<HospitalRoom> rooms = hospitalRoomService.listAllExaminationRooms();
            List<Patient> patients = patientService.listAll();
            model.addAttribute("appointment", appointment);
            model.addAttribute("patients",patients);
            model.addAttribute("doctors", doctors);
            model.addAttribute("rooms", rooms);
            model.addAttribute("pageTitle", "Edit Appointment (ID: " + id + ")");
            return "admin_view/appointment/appointment_edit_form";
        } catch (AppointmentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/appointments";
        }
    }

    @GetMapping("/appointments/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The appointment ID " + id + " has been deleted.");
        } catch (AppointmentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/appointments";
    }
}

