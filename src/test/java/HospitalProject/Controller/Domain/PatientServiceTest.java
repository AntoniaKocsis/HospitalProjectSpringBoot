package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.Patient.PatientRepository;
import HospitalProject.Controller.Domain.Patient.PatientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class PatientServiceTest {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testSave() {

        List<Patient> patientList2 = patientService.listAll();

        assert patientList2.size() == 56;

        Patient patient = new Patient();
        patient.setBirthDate(LocalDateTime.now());
        patient.setFirstName("Alex");
        patient.setLastName("Stevenson");
        patient.setAddress("Nasaud 16");
        patient.setContact("0752134322");

        patientService.save(patient);

        List<Patient> patientList = patientService.listAll();
        assert patientList.size() == 57;
        assert patientList.contains(patient);


    }
    @Test
    public void testGet() throws PatientNotFoundException {
        List<Patient> patientList2 = patientService.listAll();
        assert !patientList2.isEmpty();
        Patient patient = patientService.get(0);
        assert patient != null;

    }
    @Test
    public void testListAll() throws PatientNotFoundException {
        List<Patient> patientList2 = patientService.listAll();
        assert !patientList2.isEmpty();
        Patient patient = patientService.get(1);
        assert patientList2.contains(patient);
    }

    @Test
    public void testDelete() throws PatientNotFoundException {
        List<Patient> patientList2 = patientService.listAll();
        Patient patient = patientService.get(1);
        patientService.delete(patient.getId());
        List<Patient> patientList = patientService.listAll();
        assert patientList.size()  == patientList2.size() - 1;
    }

}
