package HospitalProject.Controller.Domain.Factory;

import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.ExaminationRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.AdmissionRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoom;
import org.springframework.stereotype.Component;

@Component
public class HospitalRoomFactory {

    public HospitalRoom createHospitalRoom(String roomType) {
        if ("examination".equalsIgnoreCase(roomType)) {
            return new ExaminationRoom();
        } else if ("admission".equalsIgnoreCase(roomType)) {
            return new AdmissionRoom();
        } else {
            // Handle unknown room types or throw an exception
            throw new IllegalArgumentException("Invalid room type: " + roomType);
        }
    }
}
