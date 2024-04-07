package HospitalProject.Controller.Domain.Doctor;

public class DoctorNotFoundException extends Throwable {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
