package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.HospitalServices.Medications.Medication;
import HospitalProject.Controller.Domain.HospitalServices.Medications.MedicationRepository;
import HospitalProject.Controller.Domain.HospitalServices.Medications.MedicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback(false) // You can adjust this based on your needs
public class MedicationServiceTest {

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private MedicationRepository medicationRepository;

    @Test
    public void testListAll() {
        List<Medication> medicationList = medicationService.listAll();
        assertThat(medicationList).isNotNull();

    }

    @Test
    public void testfilteredSearch() {

        List<Medication> medicationList = medicationService.filteredSearch("Nurofen");
        System.out.println(medicationList);

    }
}
