package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Doctor.DoctorNotFoundException;
import HospitalProject.Controller.Domain.Doctor.DoctorService;
import HospitalProject.Controller.Domain.HospitalConfiguration.Department.Department;
import HospitalProject.Controller.Domain.HospitalConfiguration.Department.DepartmentService;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.ExaminationRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomService;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.AppointmentService;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Controller
public class AppointmentSchedulingController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private HospitalRoomService hospitalRoomService;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime firstAvailableDate;

    private Patient patient;
    private Department department;
    private List<Doctor> doctors;
    private Doctor doctor;

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
        return "select_doctor";
    }

    @GetMapping("/confirm_appointment")
    public String confirmAppointment(@RequestParam("selectedDoctor") Integer selectedDoctorId, Model model) throws DoctorNotFoundException {
        doctor = doctorService.get(selectedDoctorId);
        System.out.println("Selected Doctor: " + doctor);
        List<Appointment> doctorsAppointment = new ArrayList<>();
        for (Appointment appointment : appointmentService.listAll())
            if (appointment.getDoctor() == doctor) {
                doctorsAppointment.add(appointment);
            }
        System.out.println("Doc Appointments: " + doctorsAppointment);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minDate = now.plusWeeks(1);
        firstAvailableDate = minDate;
        doctorsAppointment.sort(Comparator.comparing(Appointment::getDate));
        if (!doctorsAppointment.isEmpty()) {
            LocalDateTime latestAppointmentDate = doctorsAppointment.get(doctorsAppointment.size() - 1).getDate();
            if (latestAppointmentDate.plusHours(1).isAfter(firstAvailableDate)) {
                firstAvailableDate = latestAppointmentDate.plusHours(1);
                if (firstAvailableDate.getHour() >= 20) {
                    firstAvailableDate = firstAvailableDate.plusDays(1).withHour(9).withMinute(0); // Set it to 9am
                }
            }
        }
        ExaminationRoom examinationRoom = hospitalRoomService.availableExaminationRoom(firstAvailableDate, appointmentService.listAll());
        while (examinationRoom == null) {
            firstAvailableDate = firstAvailableDate.plusHours(1);
            if (firstAvailableDate.getHour() >= 20) {
                firstAvailableDate = firstAvailableDate.plusDays(1).withHour(9).withMinute(0); // Set it to 9am
            }
            examinationRoom = hospitalRoomService.availableExaminationRoom(firstAvailableDate, appointmentService.listAll());
        }
        System.out.println(firstAvailableDate);
        Appointment appointment = new Appointment(patient, doctor, firstAvailableDate, examinationRoom);
        model.addAttribute("appointment", appointment);
        return "confirm_appointment";
    }

    @PostMapping("/confirm_appointment/save")
    public String saveAppointment(Appointment appointment) {
        patientService.save(patient);
        appointment.setPatient(patient);
        appointment.setDate(firstAvailableDate);
        appointmentService.save(appointment);
        return "redirect:/";

    }

}
