package HospitalProject.Controller.Domain.HospitalServices.Medications;

public class MedicationNotFoundException extends Throwable {
    public MedicationNotFoundException(String message) {
        super(message);
    }
}
