package HospitalProject.Controller.Domain.HospitalServices.Prescriptions;

import HospitalProject.Controller.Domain.HospitalServices.Medications.Medication;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Patient.Patient;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "prescription_medication",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private List<Medication> medications;

    public Prescription(Doctor doctor, Patient patient, LocalDateTime date) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        medications = new ArrayList<>();
    }

    public Prescription() {
        medications = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionID=" + id +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", date=" + date +
                '}';
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void addMedication(Medication medication){
        medications.add(medication);

    }
    public boolean removeMedication(Medication medication){
        return medications.remove(medication);
    }

    public int getId() {
        return id;
    }
    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
