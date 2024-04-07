package HospitalProject.Controller.Domain.Doctor;

import HospitalProject.Controller.Domain.HospitalConfiguration.Department.Department;
import HospitalProject.Controller.Domain.HospitalServices.Admissions.Admission;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.HospitalServices.Prescriptions.Prescription;
import HospitalProject.Controller.Domain.Observer.DoctorDashboard;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "Doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String contact;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime birthDate;
    private boolean onCall;
    @ManyToMany(mappedBy = "doctors", fetch = FetchType.LAZY)
    private List<Department> departments;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Admission> admissions;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DoctorDashboard> doctorDashboards;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;

    public Doctor() {
        departments = new ArrayList<>();
        appointments = new ArrayList<>();
        admissions = new ArrayList<>();
        onCall = false;
        doctorDashboards = new ArrayList<>();
    }

    public Doctor(String firstName, String lastName, LocalDateTime birthDate, String contact, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.contact = contact;
        departments = new ArrayList<>();
        onCall = false;
    }
    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    public List<DoctorDashboard> getDoctorDashboards() {
        return doctorDashboards;
    }

    public void setDoctorDashboards(List<DoctorDashboard> doctorDashboards) {
        this.doctorDashboards = doctorDashboards;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "doctorID=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", birthDate=" + birthDate +
                '}';
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

    public int getId() {
        return id;
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


    public boolean isOnCall() {
        return onCall;
    }

    public void setOnCall(boolean onCall) {
        this.onCall = onCall;
    }

}
