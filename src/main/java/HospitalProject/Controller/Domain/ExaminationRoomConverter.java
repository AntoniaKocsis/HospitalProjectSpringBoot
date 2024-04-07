package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.ExaminationRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomNotFoundException;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExaminationRoomConverter implements Converter<String, ExaminationRoom> {

    private final HospitalRoomService hospitalRoomService;

    @Autowired
    public ExaminationRoomConverter(HospitalRoomService hospitalRoomService) {
        this.hospitalRoomService = hospitalRoomService;
    }

    @Override
    public ExaminationRoom convert(String source) {
        try {
            int roomId = Integer.parseInt(source);
           // System.out.println("Converting source to ExaminationRoom. Room ID: " + roomId);
            HospitalRoom room = hospitalRoomService.get(roomId);
            if (room instanceof ExaminationRoom) {
                //System.out.println("Conversion successful. ExaminationRoom: " + room);
                return (ExaminationRoom) room;
            } else {
                throw new RuntimeException("Retrieved room is not an instance of ExaminationRoom");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid ExaminationRoom ID format: " + source);
            throw new IllegalArgumentException("Invalid ExaminationRoom ID format", e);
        } catch (HospitalRoomNotFoundException e) {
            System.err.println("ExaminationRoom not found for ID: " + source);
            throw new RuntimeException(e);
        }
    }
}
