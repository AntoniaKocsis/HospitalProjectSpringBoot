package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Factory.HospitalRoomFactory;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.AdmissionRoom;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.ExaminationRoom;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
@SpringBootTest
@Rollback(false)
public class FactoryPatternTest {
    @Test
    public void testFactoryPattern(){
        HospitalRoomFactory hospitalRoomFactory = new HospitalRoomFactory();
        String roomType1 = "Admission";
        String roomType2 = "Examination";
        assert hospitalRoomFactory.createHospitalRoom(roomType1) instanceof AdmissionRoom;
        assert hospitalRoomFactory.createHospitalRoom(roomType2) instanceof ExaminationRoom;
    }

}
