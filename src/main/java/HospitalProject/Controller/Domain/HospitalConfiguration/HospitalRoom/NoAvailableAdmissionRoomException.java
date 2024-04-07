package HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom;

public class NoAvailableAdmissionRoomException extends Throwable {
    public NoAvailableAdmissionRoomException(String message) {
        super(message);
    }
}
