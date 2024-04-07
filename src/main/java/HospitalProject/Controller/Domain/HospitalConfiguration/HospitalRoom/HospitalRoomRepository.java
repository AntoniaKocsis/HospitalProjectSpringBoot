package HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom;

import org.springframework.data.repository.CrudRepository;

public interface HospitalRoomRepository extends CrudRepository<HospitalRoom,Integer> {
    public Long countById(Integer id);
}