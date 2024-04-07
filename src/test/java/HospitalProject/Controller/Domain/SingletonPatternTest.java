package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientRepository;
import HospitalProject.Controller.Domain.Singleton.EmergencyRoom;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


public class SingletonPatternTest {
    @Test
    public void testAddNew() {
        EmergencyRoom emergencyRoom = EmergencyRoom.getInstance();
        EmergencyRoom emergencyRoom1 = EmergencyRoom.getInstance();
        assert EmergencyRoom.getCount() == 1;
    }

}
