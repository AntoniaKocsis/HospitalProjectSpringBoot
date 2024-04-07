package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.AdmissionRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomNotFoundException;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AdmissionRoomConverter implements Converter<String, AdmissionRoom> {

    private final HospitalRoomService hospitalRoomService;

    @Autowired
    public AdmissionRoomConverter(HospitalRoomService hospitalRoomService) {
        this.hospitalRoomService = hospitalRoomService;
    }

    @Override
    public AdmissionRoom convert(String source) {
        try {
            int roomId = Integer.parseInt(source);
            // System.out.println("Converting source to AdmissionRoom. Room ID: " + roomId);
            HospitalRoom room = hospitalRoomService.get(roomId);
            if (room instanceof AdmissionRoom) {
                //System.out.println("Conversion successful. AdmissionRoom: " + room);
                return (AdmissionRoom) room;
            } else {
                throw new RuntimeException("Retrieved room is not an instance of AdmissionRoom");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid AdmissionRoom ID format: " + source);
            throw new IllegalArgumentException("Invalid AdmissionRoom ID format", e);
        } catch (HospitalRoomNotFoundException e) {
            System.err.println("AdmissionRoom not found for ID: " + source);
            throw new RuntimeException(e);
        }
    }
}
