package HospitalProject.Controller.Domain.HospitalServices.Appointments;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.ExaminationRoom;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Observer.DoctorDashboard;
import HospitalProject.Controller.Domain.Observer.PatientNotificationSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;

    public List<Appointment> listAll() {
        return (List<Appointment>) repository.findAll();
    }

    public void save(Appointment appointment)  {
        repository.save(appointment);

        DoctorDashboard doctorDashboard = new DoctorDashboard(appointment.getDoctor());
        Doctor doctor = appointment.getDoctor();
        List<DoctorDashboard> doctorDashboards = doctor.getDoctorDashboards();
        doctorDashboards.add(doctorDashboard);
        doctor.setDoctorDashboards(doctorDashboards);

        List<Appointment> doctorAppointments = doctor.getAppointments();
        doctorAppointments.add(appointment);
        doctor.setAppointments(doctorAppointments);

        PatientNotificationSystem patientNotificationSystem = new PatientNotificationSystem(appointment.getPatient());
        Patient patient = appointment.getPatient();
        List<PatientNotificationSystem> patientNotificationSystems = patient.getPatientNotificationSystems();
        patientNotificationSystems.add(patientNotificationSystem);
        patient.setPatientNotificationSystems(patientNotificationSystems);

        List<Appointment> patientAppointments = patient.getAppointments();
        patientAppointments.add(appointment);
        patient.setAppointments(patientAppointments);

        appointment.registerObserver(doctorDashboard);
        appointment.registerObserver(patientNotificationSystem);
        appointment.notifyObservers();

        ExaminationRoom examinationRoom = appointment.getExaminationRoom();
        List<Appointment> roomAppointments = examinationRoom.getAppointments();
        roomAppointments.add(appointment);
        examinationRoom.setAppointments(roomAppointments);
    }
    public Appointment get(Integer id) throws AppointmentNotFoundException {
        Optional<Appointment> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AppointmentNotFoundException("Could not find any appointment with ID " + id);
    }

    public void delete(Integer id) throws AppointmentNotFoundException {
        Long count = repository.countById(id);
        if (count == null || count == 0) {
            throw new AppointmentNotFoundException("Could not find any appointment with ID " + id);
        }
        repository.deleteById(id);

    }
}
