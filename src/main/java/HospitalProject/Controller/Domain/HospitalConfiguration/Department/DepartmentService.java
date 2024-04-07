package HospitalProject.Controller.Domain.HospitalConfiguration.Department;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired private DepartmentRepository repository;

    public List<Department> listAll() {
        return (List<Department>) repository.findAll();
    }

    public void save(Department department) {
        List<Doctor> doctors = department.getDoctors();
        for(Doctor doctor:doctors){
            List<Department>departments = doctor.getDepartments();
            departments.add(department);
            doctor.setDepartments(departments);
        }
        repository.save(department);
    }

    public Department get(Integer id) throws DepartmentNotFoundException {
        Optional<Department> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new DepartmentNotFoundException("Could not find any departments with ID " + id);
    }

    public void delete(Integer id) throws DepartmentNotFoundException {
        Long count = repository.countById(id);
        if (count == null || count == 0) {
            throw new DepartmentNotFoundException("Could not find any departments with ID " + id);
        }
        repository.deleteById(id);

    }
}
