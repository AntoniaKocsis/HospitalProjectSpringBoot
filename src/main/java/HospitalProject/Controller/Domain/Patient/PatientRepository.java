package HospitalProject.Controller.Domain.Patient;

import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient,Integer> {
    public Long countById(Integer id);
}
