package HospitalProject.Controller.Domain.Singleton;

import HospitalProject.Controller.Domain.Interfaces.StrategyPattern.PatientHandlingStrategy;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.Patient.PatientService;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomService;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.NoAvailableAdmissionRoomException;
import HospitalProject.Controller.Domain.HospitalServices.Admissions.AdmissionService;
import HospitalProject.Controller.Domain.HospitalServices.Prescriptions.PrescriptionService;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Doctor.DoctorNotFoundException;
import HospitalProject.Controller.Domain.Doctor.DoctorService;
import HospitalProject.Controller.Domain.PatientStateEnum.PatientCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class EmergencyRoomService {
    private EmergencyRoom emergencyRoom;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AdmissionService admissionService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private HospitalRoomService hospitalRoomService;

    @PostConstruct
    public void initialize() {
        emergencyRoom = EmergencyRoom.getInstance();
        emergencyRoom.resetWaitingList();
        emergencyRoom.setDoctorsOnCall(doctorService.listOnCall());
    }


    public void addToWaitingList(Patient patient, String condition) {
        patientService.save(patient);
        emergencyRoom.addToWaitingList(patient, condition);
    }

    public Patient getPatient(Integer id) throws PatientNotFoundException {
        return patientService.get(id);
    }

    public Doctor getDoctor(Integer id) throws DoctorNotFoundException {
        return doctorService.get(id);
    }

    public void removeFromWaitingList(Integer id) throws PatientNotFoundException {
        if (!emergencyRoom.removeFromWaitingList(id))
            throw new PatientNotFoundException("Patient with the given ID not found");
    }

    public Map<Patient, String> listWaitingList() {
        Map<Patient, String> patientConditionMap = new HashMap<>();
        List<PatientInfo> waitingList = emergencyRoom.getWaitingList();
        Collections.sort(waitingList, Comparator.comparingInt(PatientInfo::getConditionPriority));
        for(PatientInfo patientInfo:waitingList){
            if(patientInfo.getCondition() == PatientCondition.CRITICAL)
            patientConditionMap.put(patientInfo.getPatient(),"CRITICAL");
            else if (patientInfo.getCondition() == PatientCondition.SERIOUS) {
                patientConditionMap.put(patientInfo.getPatient(),"SERIOUS");
            } else{
                patientConditionMap.put(patientInfo.getPatient(),"STABLE");
            }
        }
        return patientConditionMap;
    }

    public void addOnCallDoctor(Doctor doctor) {
        doctor.setOnCall(true);
        doctorService.save(doctor);
        emergencyRoom.setDoctorsOnCall(doctorService.listOnCall());
    }

    public void removeOnCallDoctor(Integer id) throws DoctorNotFoundException {
        Doctor doctor = getDoctor(id);
        doctor.setOnCall(false);
        doctorService.save(doctor);
        emergencyRoom.setDoctorsOnCall(doctorService.listOnCall());
    }

    public List<Doctor> listOnCallDoctors() {
        return emergencyRoom.getDoctorsOnCall();
    }
    public boolean handleNextPatient() throws NoAvailableAdmissionRoomException {
        return emergencyRoom.handleNextPatient(admissionService,prescriptionService,hospitalRoomService);
    }
    public PatientHandlingStrategy getStrategy(){
        return emergencyRoom.getStrategy();
    }

}

