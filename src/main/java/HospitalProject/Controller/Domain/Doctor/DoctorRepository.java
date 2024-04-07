package HospitalProject.Controller.Domain.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor,Integer> {
    public Long countById(Integer id);
}