package HospitalProject.Controller.Domain.Singleton;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.PatientStateEnum.PatientCondition;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class PatientInfo{
    private Patient patient;
    private PatientCondition patientCondition;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    public PatientInfo(Patient patient, PatientCondition condition,LocalDateTime date) {
        this.patient = patient;
        this.patientCondition = condition;
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public PatientCondition getCondition() {
        return patientCondition;
    }
    public int getConditionPriority() {
        switch (patientCondition) {
            case CRITICAL:
                return 0;
            case SERIOUS:
                return 1;
            case STABLE:
                return 2;
            default:
                return Integer.MAX_VALUE; // Handle other conditions if needed
        }
    }

    @Override
    public String toString() {
        return "PatientInfo{" +
                "patient=" + patient +
                ", patientCondition=" + patientCondition +
                ", date=" + date +
                '}';
    }
}