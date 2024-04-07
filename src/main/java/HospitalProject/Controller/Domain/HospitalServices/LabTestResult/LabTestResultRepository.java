package HospitalProject.Controller.Domain.HospitalServices.LabTestResult;

import org.springframework.data.repository.CrudRepository;

public interface LabTestResultRepository extends CrudRepository<LabTestResult,Integer> {
public Long countById(Integer id);
        }
