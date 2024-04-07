package HospitalProject.Controller.Domain.Singleton;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomService;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.NoAvailableAdmissionRoomException;
import HospitalProject.Controller.Domain.HospitalServices.Admissions.AdmissionService;
import HospitalProject.Controller.Domain.HospitalServices.Prescriptions.PrescriptionService;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Interfaces.StrategyPattern.PatientHandlingStrategy;
import HospitalProject.Controller.Domain.PatientStateEnum.PatientCondition;
import HospitalProject.Controller.Domain.Strategy.CriticalConditionStrategy;
import HospitalProject.Controller.Domain.Strategy.NonCriticalConditionStrategy;

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;


public class EmergencyRoom {

    private PatientHandlingStrategy strategy;
    private static int count = 0;
    private static EmergencyRoom instance;
    private List<Doctor> doctorsOnCall;
    private List<PatientInfo> waitingList;

    private EmergencyRoom() {
        count++;
        waitingList = new ArrayList<>();
        doctorsOnCall = new ArrayList<>();
    }
    public static EmergencyRoom getInstance() {
        if (instance == null) {
            instance = new EmergencyRoom();
        }
        return instance;
    }

    public void setStrategy(PatientHandlingStrategy strategy) {
        this.strategy = strategy;
    }
    public List<Doctor> getDoctorsOnCall() {
        return doctorsOnCall;
    }

    public void setDoctorsOnCall(List<Doctor> doctorsOnCall) {
        this.doctorsOnCall = doctorsOnCall;
    }

    public List<PatientInfo> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(List<PatientInfo> waitingList) {
        this.waitingList = waitingList;
    }

    public Doctor assignDoctor() {
        Random random = new Random();

        int randomIndex = random.nextInt(doctorsOnCall.size());

        return doctorsOnCall.get(randomIndex);
    }

    public void resetWaitingList() {
        this.setWaitingList(new ArrayList<>());
    }
    public void addToWaitingList(Patient patient, String condition) {
        if (!Objects.equals(condition, "CRITICAL") && !Objects.equals(condition, "SERIOUS") && !Objects.equals(condition, "STABLE"))
            throw new IllegalArgumentException("Invalid patient condition");

        LocalDateTime date = LocalDateTime.now();
        PatientCondition patientCondition;

        if (Objects.equals(condition, "CRITICAL")) {
            patientCondition = PatientCondition.CRITICAL;
        } else if (Objects.equals(condition, "STABLE")) {
            patientCondition = PatientCondition.STABLE;
        } else {
            patientCondition = PatientCondition.SERIOUS;
        }

        waitingList.add(new PatientInfo(patient, patientCondition, date));
        Collections.sort(waitingList, Comparator.comparingInt(PatientInfo::getConditionPriority));
    }


    public boolean removeFromWaitingList(Integer id) throws PatientNotFoundException {
        for (PatientInfo patientInfo : waitingList) {
            if (Objects.equals(patientInfo.getPatient().getId(), id)) {
                return waitingList.remove(patientInfo);
            }
        }
        throw new PatientNotFoundException("No patient with the given ID");
    }

    public boolean handleNextPatient(AdmissionService admissionService, PrescriptionService prescriptionService, HospitalRoomService hospitalRoomService) throws NoAvailableAdmissionRoomException {
        if (waitingList.isEmpty())
            return false;
        PatientInfo patientInfo = waitingList.get(0);
        LocalDateTime date = patientInfo.getDate();
        Patient patient = waitingList.get(0).getPatient();
        PatientCondition patientCondition = waitingList.get(0).getCondition();
        waitingList.remove(patientInfo);
        Doctor assignedDoctor = assignDoctor();
        if (patientCondition == PatientCondition.CRITICAL || patientCondition == PatientCondition.SERIOUS) {
//            Admission admission = new Admission(patient, assignedDoctor, date);
//            AdmissionRoom admissionRoom = (AdmissionRoom) hospitalRoomService.firstAvailableAdmissionRoom();
//            admission.setAdmissionRoom(admissionRoom);
//            admissionService.save(admission);
            setStrategy(new CriticalConditionStrategy());
        } else if (patientCondition == PatientCondition.STABLE) {
//            Prescription prescription = new Prescription(assignedDoctor, patient, date);
//            List<Medication> medications = new ArrayList<>();
//            prescription.setMedications(medications);
//            prescriptionService.save(prescription);
            setStrategy(new NonCriticalConditionStrategy());
        }
        strategy.handlePatient(patient, assignedDoctor);
        return true;

    }

    public static int getCount() {
        return count;
    }

    public PatientHandlingStrategy getStrategy() {
        return strategy;
    }
}
