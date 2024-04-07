package HospitalProject.Controller.Domain.Strategy;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Interfaces.StrategyPattern.PatientHandlingStrategy;

public class CriticalConditionStrategy implements PatientHandlingStrategy {
    @Override
    public void handlePatient(Patient patient, Doctor doctor) {
        System.out.println("Doctor" +  doctor + " Admitting patient " + patient + " with CRITICAL/SERIOUS condition.");
    }
}
