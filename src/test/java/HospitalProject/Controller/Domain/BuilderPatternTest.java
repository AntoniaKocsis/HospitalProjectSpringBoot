package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.HospitalServices.LabTestResult.LabTestResult;
import HospitalProject.Controller.Domain.HospitalServices.LabTestResult.LabTestResultRepository;
import HospitalProject.Controller.Domain.HospitalServices.LabTestResult.LabTestResultService;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.Patient.PatientRepository;
import HospitalProject.Controller.Domain.Patient.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@Rollback(false)
public class BuilderPatternTest {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;
    @Test
    public void testBuilderPattern() throws PatientNotFoundException {
        Patient patient = patientService.get(1);
        LocalDateTime dateTime = LocalDateTime.now();
        LabTestResult labTestResult = new LabTestResult.LabTestResultBuilder()
                .withPatient(patient)
                .withTestName("Blood Test")
                .withTestDate(dateTime)
                .withResult("Normal")
                .build();

        assert Objects.equals(labTestResult.getTestName(), "Blood Test");
    }
}
