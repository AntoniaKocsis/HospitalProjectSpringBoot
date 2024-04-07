package HospitalProject.Controller.Domain.HospitalServices.LabTestResult;
import HospitalProject.Controller.Domain.Patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabTestResultService {
    @Autowired
    private LabTestResultRepository repository;

    public List<LabTestResult> listAll() {
        return (List<LabTestResult>) repository.findAll();
    }

    public void save(LabTestResult labTestResult)  {
        repository.save(labTestResult);
        Patient patient = labTestResult.getPatient();
        List<LabTestResult>labTestResults = patient.getLabTestResults();
        labTestResults.add(labTestResult);
        patient.setLabTestResults(labTestResults);
    }
    public LabTestResult get(Integer id) throws LabTestResultNotFoundException {
        Optional<LabTestResult> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new LabTestResultNotFoundException("Could not find any labTestResult with ID " + id);
    }

    public void delete(Integer id) throws LabTestResultNotFoundException {
        Long count = repository.countById(id);
        if (count == null || count == 0) {
            throw new LabTestResultNotFoundException("Could not find any labTestResult with ID " + id);
        }
        repository.deleteById(id);

    }
}
