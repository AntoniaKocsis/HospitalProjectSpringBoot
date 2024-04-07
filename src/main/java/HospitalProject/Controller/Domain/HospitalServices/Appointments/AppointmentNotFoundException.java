package HospitalProject.Controller.Domain.HospitalServices.Appointments;

public class AppointmentNotFoundException extends Throwable {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
