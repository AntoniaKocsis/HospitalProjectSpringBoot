package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientRepository;
import HospitalProject.Controller.Domain.HospitalServices.Medications.Medication;
import HospitalProject.Controller.Domain.HospitalServices.Medications.MedicationRepository;
import HospitalProject.Controller.Domain.HospitalServices.Prescriptions.Prescription;
import HospitalProject.Controller.Domain.HospitalServices.Prescriptions.PrescriptionRepository;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Doctor.DoctorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class PrescriptionRepositoryTest {
    @Autowired private PrescriptionRepository repository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private PatientRepository patientRepository;
    @Autowired private MedicationRepository medicationRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void testAddNew()  {

        String dateString = "2001-12-31T23:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        Prescription prescription = new Prescription();
        Doctor doctor = new Doctor();
        doctor.setBirthDate(localDateTime);
        doctor.setFirstName("Mihai");
        doctor.setLastName("Stevenson");
        doctor.setAddress("Nasaud 16");
        doctor.setContact("0752134322");
        doctorRepository.save(doctor);
        Patient patient = new Patient();
        patient.setBirthDate(localDateTime);
        patient.setFirstName("Alex");
        patient.setLastName("Stevenson");
        patient.setAddress("Nasaud 16");
        patient.setContact("0752134322");
        patientRepository.save(patient);
        Medication medication = new Medication();
        medication.setName("Nurofen");
        medication.setConcentration(400);
        medicationRepository.save(medication);


        prescription.setDate(localDateTime);
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.addMedication(medication);


        Prescription savedPrescription = repository.save(prescription);

        Assertions.assertThat(savedPrescription).isNotNull();
        Assertions.assertThat(savedPrescription.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<Prescription> prescriptions = repository.findAll();
        Assertions.assertThat(prescriptions).hasSizeGreaterThan(0);

        for (Prescription prescription : prescriptions) {
            System.out.println(prescription);
        }
    }

    @Test
    public void testUpdate() {
        Integer prescriptionID = 4;
        Optional<Prescription> optionalPrescription = repository.findById(prescriptionID);

        Prescription prescription = optionalPrescription.orElseThrow(() ->
                new RuntimeException("Prescription not found for ID: " + prescriptionID)
        );

        Medication medication = new Medication();
        medication.setConcentration(200);
        medication.setName("Paracetamol");
        medicationRepository.save(medication);

        List<Medication> medications = prescription.getMedications();
        medications.add(medication);

        prescription.setMedications(medications);

        Prescription savedPrescription = repository.save(prescription);
        entityManager.flush();

        Prescription updatedPrescription = repository.findById(savedPrescription.getId())
                .orElseThrow(() -> new RuntimeException("Failed to retrieve updated prescription"));

        Assertions.assertThat(updatedPrescription).isNotNull();
        Assertions.assertThat(updatedPrescription.getMedications().contains(medication));
    }


//    @Test
//    public void testGet() {
//        Integer prescriptionId = 1;
//        Optional<Prescription> optionalPrescription = repository.findById(prescriptionId);
//        Assertions.assertThat(optionalPrescription).isPresent();
//        System.out.println(optionalPrescription.get());
//    }
//
    @Test
    public void testDelete() {
        Integer userId = 3;
        repository.deleteById(userId);

        Optional<Prescription> optionalUser = repository.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
