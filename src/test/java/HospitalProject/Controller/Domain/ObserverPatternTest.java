package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Doctor.DoctorNotFoundException;
import HospitalProject.Controller.Domain.Doctor.DoctorRepository;
import HospitalProject.Controller.Domain.Doctor.DoctorService;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomRepository;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomService;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.AppointmentRepository;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.ExaminationRoom;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.AppointmentService;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.Patient.PatientRepository;
import HospitalProject.Controller.Domain.Patient.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@Rollback
public class ObserverPatternTest {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private HospitalRoomService hospitalRoomService;

    @Autowired
    private HospitalRoomRepository hospitalRoomRepository;


    @Test
    public void testSave() throws DoctorNotFoundException, PatientNotFoundException {
        // Create entities for testing
        Doctor doctor = doctorService.get(1);
        Patient patient = patientService.get(1);
        ExaminationRoom examinationRoom = (ExaminationRoom) hospitalRoomService.listAllExaminationRooms().get(1);

        Appointment appointment = new Appointment();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setExaminationRoom(examinationRoom);
        appointment.setDate(LocalDateTime.now());
        appointmentService.save(appointment);
        assertEquals(appointment.getObservers().size(),2);
    }
}
