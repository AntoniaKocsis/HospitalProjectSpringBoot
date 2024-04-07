package HospitalProject.Controller.Domain.Billing;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.Patient.PatientService;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.AppointmentService;
import HospitalProject.Controller.Domain.HospitalServices.LabTestResult.LabTestResult;
import HospitalProject.Controller.Domain.HospitalServices.LabTestResult.LabTestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HospitalBillingSystemService {
    private HospitalBillingSystem hospitalBillingSystem;
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private LabTestResultService labTestResultService;

    @Autowired
    private PatientService patientService;

    @PostConstruct
    public void initialize() {
        hospitalBillingSystem = HospitalBillingSystem.getInstance();
    }
    public Map<Appointment,Double> listAllAppointmentBills(){
        Map<Appointment,Double> bills = new HashMap<>();
        List<Appointment> appointments = appointmentService.listAll();
        for(Appointment appointment:appointments){
            bills.put(appointment, hospitalBillingSystem.patientAppointmentBill());
        }
        return bills;
    }
    public Map<LabTestResult,Double> listAllTestsBills(){
        Map<LabTestResult,Double> bills = new HashMap<>();
        List<LabTestResult> labTestResults = labTestResultService.listAll();
        for(LabTestResult labTestResult:labTestResults){
            bills.put(labTestResult, hospitalBillingSystem.patientTestsBill());
        }
        return bills;
    }
    public Map<Appointment,Double> listPatientAppointmentBills(Integer id) throws PatientNotFoundException {
        Patient patient = patientService.get(id);
        Map<Appointment,Double> bills = new HashMap<>();
        List<Appointment> appointments = appointmentService.listAll();
        for(Appointment appointment:appointments){
            if(appointment.getPatient() == patient){
                bills.put(appointment, hospitalBillingSystem.patientAppointmentBill());
            }
        }
        return bills;
    }
    public Map<LabTestResult,Double> listPatientTestBills(Integer id) throws PatientNotFoundException {
        Patient patient = patientService.get(id);
        Map<LabTestResult,Double> bills = new HashMap<>();
        List<LabTestResult> labTestResults = labTestResultService.listAll();
        for(LabTestResult labTestResult:labTestResults){
            if(labTestResult.getPatient() == patient) {
                bills.put(labTestResult, hospitalBillingSystem.patientTestsBill());
            }
        }
        return bills;
    }
}
