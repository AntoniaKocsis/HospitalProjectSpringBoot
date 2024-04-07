package HospitalProject.Controller.Domain.Strategy;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Interfaces.StrategyPattern.PatientHandlingStrategy;

public class NonCriticalConditionStrategy implements PatientHandlingStrategy {
    @Override
    public void handlePatient(Patient patient, Doctor doctor) {
        System.out.println("Doctor: "+doctor+" Writing prescription for patient: " + patient+ " with NON-CRITICAL condition.");
    }
}
