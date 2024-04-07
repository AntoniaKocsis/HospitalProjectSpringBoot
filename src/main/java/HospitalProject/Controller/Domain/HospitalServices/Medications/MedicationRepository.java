package HospitalProject.Controller.Domain.HospitalServices.Medications;

import org.springframework.data.repository.CrudRepository;

public interface MedicationRepository extends CrudRepository<Medication,Integer> {
    public Long countById(Integer id);
}
