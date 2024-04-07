package HospitalProject.Controller.Domain.HospitalServices.Admissions;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.AdmissionRoom;
import HospitalProject.Controller.Domain.Patient.Patient;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Admissions")
public class Admission {
    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime enrollmentDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dischargingDate;


    // Patient - Admission Relationship
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;


    // Doctor - Admission Relationship

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;



    // AdmissionRoom - Admission Relationship
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "room_id", nullable = false)
    private AdmissionRoom admissionRoom;

    public Admission() {}
    public Admission(Patient patient, Doctor doctor, LocalDateTime date) {
        this.patient = patient;
        this.doctor = doctor;
        this.enrollmentDate = date;
        this.admissionRoom = null;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDischargingDate() {
        return dischargingDate;
    }

    public void setDischargingDate(LocalDateTime dischargingDate) {
        this.dischargingDate = dischargingDate;
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

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public AdmissionRoom getAdmissionRoom() {
        return admissionRoom;
    }

    public void setAdmissionRoom(AdmissionRoom admissionRoom) {
        this.admissionRoom = admissionRoom;
        admissionRoom.setAvailable(false);
    }

    @Override
    public String toString() {
        return "Admission{" +
                "admissionID=" + id +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", enrollmentDate=" + enrollmentDate +
                ", dischargingDate=" + dischargingDate +
                ", admissionRoom=" + admissionRoom +
                '}';
    }

}
