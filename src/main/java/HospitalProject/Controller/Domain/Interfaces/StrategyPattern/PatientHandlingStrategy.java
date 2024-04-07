package HospitalProject.Controller.Domain.Interfaces.StrategyPattern;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Patient.Patient;

public interface PatientHandlingStrategy {
    void handlePatient(Patient patient, Doctor doctor);
}
