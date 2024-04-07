package HospitalProject.Controller.Domain.Observer;

import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.Patient.Patient;
import javax.persistence.*;

import java.util.ArrayList;

@Entity
@DiscriminatorValue("PatientNotificationSystem")
public class PatientNotificationSystem extends Observer {
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    public PatientNotificationSystem(){}

    public PatientNotificationSystem(Patient patient) {
        super();
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "PatientNotificationSystem{" +
                "patient=" + patient +
                '}';
    }

    @Override
    public void update(Appointment appointment) {
        ArrayList<Appointment> appointments = super.getAppointments();
        appointments.add(appointment);
        super.setAppointments(appointments);
        System.out.println(patient.getFirstName()+" " + patient.getLastName() + " - Latest appointment update: " + appointment);
    }
}
