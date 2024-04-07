package HospitalProject.Controller.Domain.HospitalServices.Admissions;

import org.springframework.data.repository.CrudRepository;

public interface AdmissionRepository extends CrudRepository<Admission,Integer> {
    public Long countById(Integer id);
}
