package HospitalProject.Controller.Domain;

public class UserNotFoundException  extends Throwable {
    public UserNotFoundException(String message) {
        super(message);
    }
}

