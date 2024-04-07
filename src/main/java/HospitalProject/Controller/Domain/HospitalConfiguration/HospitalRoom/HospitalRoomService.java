package HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalRoomService {
    @Autowired private HospitalRoomRepository repository;

    public List<HospitalRoom> listAll() {
        return (List<HospitalRoom>) repository.findAll();
    }
    public List<HospitalRoom> listAllExaminationRooms(){
        List<HospitalRoom> examinationRooms = new ArrayList<>();
        for(HospitalRoom hospitalRoom: repository.findAll()){
            if(hospitalRoom instanceof ExaminationRoom)
                examinationRooms.add(hospitalRoom);
        }
        return examinationRooms;
    }
    public List<HospitalRoom> listAllAdmissionRooms(){
        List<HospitalRoom> admissionRooms = new ArrayList<>();
        for(HospitalRoom hospitalRoom: repository.findAll()){
            if(hospitalRoom instanceof AdmissionRoom)
                admissionRooms.add(hospitalRoom);
        }
        return admissionRooms;
    }
    public List<HospitalRoom> listAvailableAdmissionRooms(){
        List<HospitalRoom> admissionRooms = new ArrayList<>();
        for(HospitalRoom hospitalRoom: repository.findAll()){
            if(hospitalRoom instanceof AdmissionRoom && ((AdmissionRoom) hospitalRoom).isAvailable())
                admissionRooms.add(hospitalRoom);
        }
        return admissionRooms;
    }
    public HospitalRoom firstAvailableAdmissionRoom() throws NoAvailableAdmissionRoomException {
        for(HospitalRoom hospitalRoom: repository.findAll()){
            if(hospitalRoom instanceof AdmissionRoom && ((AdmissionRoom) hospitalRoom).isAvailable())
                return hospitalRoom;
        }
        throw new NoAvailableAdmissionRoomException("No admission room available...");
    }
    public void save(HospitalRoom hospitalRoom) {
        repository.save(hospitalRoom);
    }

    public HospitalRoom get(Integer id) throws HospitalRoomNotFoundException {
        Optional<HospitalRoom> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new HospitalRoomNotFoundException("Could not find any rooms with ID " + id);
    }

    public void delete(Integer id) throws HospitalRoomNotFoundException {
        Long count = repository.countById(id);
        if (count == null || count == 0) {
            throw new HospitalRoomNotFoundException("Could not find any users with ID " + id);
        }
        repository.deleteById(id);

    }
}
