package HospitalProject.Controller.Domain.HospitalServices.Appointments;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Observer.DoctorDashboard;
import HospitalProject.Controller.Domain.Observer.Observer;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.ExaminationRoom;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import HospitalProject.Controller.Domain.Observer.PatientNotificationSystem;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Table(name = "Appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Appointment - Patient Relationship
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Appointment - Doctor Relationship
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    // Appointment - ExaminationRoom Relationship
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "room_id", nullable = false)
    private ExaminationRoom examinationRoom;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "appointment_observer",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "observer_id")
    )
    private List<Observer> observers;

    /**             Constructor           **/

    public Appointment() {
        observers = new ArrayList<>();
    }

    public Appointment(Patient patient, Doctor doctor, LocalDateTime date, ExaminationRoom room) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.examinationRoom = room;
        DoctorDashboard doctorDashboard = new DoctorDashboard(doctor);
        PatientNotificationSystem patientNotificationSystem = new PatientNotificationSystem(patient);
        observers = new ArrayList<>();
        this.registerObserver(doctorDashboard);
        this.registerObserver(patientNotificationSystem);
        this.notifyObservers();

    }



    /**             Getter - setter - toString           **/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public ExaminationRoom getExaminationRoom() {
        return examinationRoom;
    }

    public void setExaminationRoom(ExaminationRoom examinationRoom) {
        this.examinationRoom = examinationRoom;
    }
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + id +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", date=" + date +
                ", room=" + examinationRoom +
                '}';
    }

    /**             For observer pattern           **/

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public List<Observer> getObservers() {
        return observers;
    }
}
