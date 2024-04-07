package HospitalProject.Controller.Domain.HospitalServices.Prescriptions;

import org.springframework.data.repository.CrudRepository;

public interface PrescriptionRepository extends CrudRepository<Prescription,Integer> {
    public Long countById(Integer id);
}
