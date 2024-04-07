package HospitalProject.Controller.Domain.Patient;

import HospitalProject.Controller.Domain.HospitalServices.Admissions.Admission;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.HospitalServices.LabTestResult.LabTestResult;
import HospitalProject.Controller.Domain.HospitalServices.Prescriptions.Prescription;
import HospitalProject.Controller.Domain.Observer.PatientNotificationSystem;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Patients")
public class Patient {

    /** Attributes **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime birthDate;

    private String contact;
    private String address;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Admission> admissions;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PatientNotificationSystem> patientNotificationSystems;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LabTestResult> labTestResults;

    /** Constructors **/
    public Patient() {
        appointments = new ArrayList<>();
        admissions = new ArrayList<>();
        prescriptions = new ArrayList<>();
        patientNotificationSystems = new ArrayList<>();
        labTestResults = new ArrayList<>();
    }

    public Patient(String contact, String address, String firstName, String lastName, LocalDateTime birthDate) {
        this.contact = contact;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        appointments = new ArrayList<>();
        admissions = new ArrayList<>();
        patientNotificationSystems = new ArrayList<>();
    }

    /** Getters and Setters **/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    public List<PatientNotificationSystem> getPatientNotificationSystems() {
        return patientNotificationSystems;
    }

    public void setPatientNotificationSystems(List<PatientNotificationSystem> patientNotificationSystems) {
        this.patientNotificationSystems = patientNotificationSystems;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public List<LabTestResult> getLabTestResults() {
        return labTestResults;
    }

    public void setLabTestResults(List<LabTestResult> labTestResults) {
        this.labTestResults = labTestResults;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
