package HospitalProject.Controller.Domain.HospitalServices.Prescriptions;

public class PrescriptionNotFoundException extends Throwable {
    public PrescriptionNotFoundException(String message) {
        super(message);
    }
}
