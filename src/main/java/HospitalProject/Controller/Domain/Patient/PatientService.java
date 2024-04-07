package HospitalProject.Controller.Domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired private PatientRepository repository;

    public List<Patient> listAll() {
        return (List<Patient>) repository.findAll();
    }

    public void save(Patient patient) {
        repository.save(patient);
    }

    public Patient get(Integer id) throws PatientNotFoundException {
        Optional<Patient> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new PatientNotFoundException("Could not find any patients with ID " + id);
    }

    public void delete(Integer id) throws PatientNotFoundException {
        Long count = repository.countById(id);
        if (count == null || count == 0) {
            throw new PatientNotFoundException("Could not find any patients with ID " + id);
        }
        repository.deleteById(id);

    }
}
