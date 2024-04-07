package HospitalProject.Controller.Domain.Observer;

import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import javax.persistence.*;

import java.util.ArrayList;

@Entity
@DiscriminatorValue("DoctorDashboard")
public class DoctorDashboard extends Observer {

    // Doctor and DoctorDashboard Relationship

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;


    public DoctorDashboard(){}
    public DoctorDashboard(Doctor doctor) {
        super();
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "DoctorDashboard{" +
                "doctor=" + doctor +
                '}';
    }

    @Override
    public void update(Appointment appointment) {
        ArrayList<Appointment> appointments = super.getAppointments();
        appointments.add(appointment);
        super.setAppointments(appointments);
        System.out.println(doctor.getFirstName() + " " + doctor.getLastName() + " - Latest update: " + appointment);
    }
}
