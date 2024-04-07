package HospitalProject.Controller.Domain.HospitalServices.Prescriptions;
import HospitalProject.Controller.Domain.HospitalServices.Medications.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    public List<Prescription> listAll() {
        return (List<Prescription>) prescriptionRepository.findAll();
    }

    public void save(Prescription prescription) {
        List<Medication> medications = prescription.getMedications();
        for(Medication medication: medications) {
            List<Prescription> prescriptions = medication.getPrescriptions();
            prescriptions.add(prescription);
            medication.setPrescriptions(prescriptions);
        }
        prescriptionRepository.save(prescription);
    }

    public Prescription get(Integer id) throws PrescriptionNotFoundException {
        Optional<Prescription> result = prescriptionRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new PrescriptionNotFoundException("Could not find any prescription with ID " + id);
    }

    public void delete(Integer id) throws PrescriptionNotFoundException {
        Long count = prescriptionRepository.countById(id);
        if (count == null || count == 0) {
            throw new PrescriptionNotFoundException("Could not find any prescription with ID " + id);
        }
        prescriptionRepository.deleteById(id);

    }
}

