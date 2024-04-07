package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Doctor.DoctorNotFoundException;
import HospitalProject.Controller.Domain.Doctor.DoctorRepository;
import HospitalProject.Controller.Domain.Doctor.DoctorService;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.NoAvailableAdmissionRoomException;
import HospitalProject.Controller.Domain.HospitalServices.Medications.Medication;
import HospitalProject.Controller.Domain.HospitalServices.Medications.MedicationRepository;
import HospitalProject.Controller.Domain.HospitalServices.Medications.MedicationService;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.Patient.PatientRepository;
import HospitalProject.Controller.Domain.Patient.PatientService;
import HospitalProject.Controller.Domain.Singleton.EmergencyRoom;
import HospitalProject.Controller.Domain.Singleton.EmergencyRoomService;
import HospitalProject.Controller.Domain.Strategy.CriticalConditionStrategy;
import HospitalProject.Controller.Domain.Strategy.NonCriticalConditionStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback(false)
public class StrategyPatternTest {

    @Autowired
    private EmergencyRoomService service;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testInitialise() throws DoctorNotFoundException {
        assert EmergencyRoom.getCount() == 1;
        List<Doctor> onCallDoctors = service.listOnCallDoctors();
        assert onCallDoctors.size() == doctorService.listOnCall().size();
        Map<Patient,String> waitingList = service.listWaitingList();
        assert waitingList.isEmpty();
    }
    @Test
    public void testStrategyPattern() throws PatientNotFoundException, NoAvailableAdmissionRoomException {
        // Add to waiting list 2 patients
        // One of them is stable, and the other one has a SERIOUS Condition
        Patient patient = patientService.get(1);
        Patient patient2 = patientService.get(2);
        service.addToWaitingList(patient,"STABLE");
        service.addToWaitingList(patient2,"SERIOUS");
        service.handleNextPatient();
        assert service.getStrategy() instanceof CriticalConditionStrategy;
        assert service.listWaitingList().containsKey(patient);
        service.handleNextPatient();
        assert service.getStrategy() instanceof NonCriticalConditionStrategy;
        assert service.listWaitingList().isEmpty();
    }
}
