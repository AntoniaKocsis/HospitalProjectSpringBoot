package HospitalProject.Controller.Domain.HospitalServices.Appointments;

import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment,Integer> {
    public Long countById(Integer id);
}
