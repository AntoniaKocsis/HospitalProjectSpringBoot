package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class PatientRepositoryTest {
    @Autowired private PatientRepository repository;

    @Test
    public void testAddNew() {

        String dateString = "2001-12-31T23:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Parse the string into a LocalDateTime object
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        Patient patient = new Patient();
        patient.setBirthDate(localDateTime);
        patient.setFirstName("Alex");
        patient.setLastName("Stevenson");
        patient.setAddress("Nasaud 16");
        patient.setContact("0752134322");

        Patient savedUser = repository.save(patient);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<Patient> patients = repository.findAll();
        Assertions.assertThat(patients).hasSizeGreaterThan(0);

        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    @Test
    public void testUpdate() {
        Integer userId = 14;
        Optional<Patient> optionalUser = repository.findById(userId);
        Patient user = optionalUser.get();
        user.setFirstName("Antonia");
        repository.save(user);

        Patient updatedUser = repository.findById(userId).get();
        Assertions.assertThat(updatedUser.getFirstName()).isEqualTo("Antonia");
    }

    @Test
    public void testGet() {
        Integer userId = 13;
        Optional<Patient> optionalUser = repository.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Integer userId = 13;
        repository.deleteById(userId);

        Optional<Patient> optionalUser = repository.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
