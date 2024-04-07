package HospitalProject.Controller.Domain.HospitalConfiguration.Department;

import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department,Integer> {
    public Long countById(Integer id);
}
