package HospitalProject.Controller.Domain.Billing;

import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.HospitalServices.LabTestResult.LabTestResult;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.Patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class HospitalBillingSystemController {
    @Autowired private HospitalBillingSystemService service;
    @Autowired private PatientService patientService;

    @GetMapping("/hospitalBillingSystems")
    public String showTestBills(Model model) {
        Map<LabTestResult,Double> testBills =  service.listAllTestsBills();
        model.addAttribute("testBills", testBills);

        return "admin_view/billingSystem/hospitalBillingSystems";
    }

    @GetMapping("/hospitalBillingSystems/appointments")
    public String showAppointmentBills(Model model) {
        Map<Appointment,Double> appointmentBills =  service.listAllAppointmentBills();
        model.addAttribute("appointmentBills", appointmentBills);

        return "admin_view/billingSystem/hospitalBillingSystems_appointments";
    }
    @GetMapping("/hospitalBillingSystems/select_patient")
    public String selectPatientForTestBills(Model model) {
        List<Patient> patients = patientService.listAll();
        model.addAttribute("patients", patients);
        model.addAttribute("pageTitle", "Choose Patient");
        return "admin_view/billingSystem/select_patient";
    }

    @GetMapping("/hospitalBillingSystems/select_patient_for_appointment")
    public String selectPatientForAppointmentBills(Model model) {
        List<Patient> patients = patientService.listAll();
        model.addAttribute("patients", patients);
        model.addAttribute("pageTitle", "Choose Patient");
        return "admin_view/billingSystem/select_patient_for_appointment";
    }
    @GetMapping("/hospitalBillingSystems/patient_tests")
    public String showTestBillsForPatient(@RequestParam Integer patientId,Model model) throws PatientNotFoundException {
        Map<LabTestResult,Double> testBills =  service.listPatientTestBills(patientId);
        model.addAttribute("testBills", testBills);
        return "admin_view/billingSystem/hospitalBillingSystems_patient_tests";
    }
    @GetMapping("/hospitalBillingSystems/patient_appointments")
    public String showAppointmentBillsForPatient(@RequestParam Integer patientId,Model model) throws PatientNotFoundException {
        Map<Appointment,Double> appointmentBills =  service.listPatientAppointmentBills(patientId);
        model.addAttribute("appointmentBills", appointmentBills);

        return "admin_view/billingSystem/hospitalBillingSystems_patient_appointments";
    }

}

