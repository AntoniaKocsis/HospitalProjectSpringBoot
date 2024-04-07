package HospitalProject.Controller.Domain.Observer;

import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.Interfaces.ObserverPattern.AppointmentObserver;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Observers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "observer_type", discriminatorType = DiscriminatorType.STRING)
public class Observer implements AppointmentObserver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany(mappedBy = "observers",fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    public ArrayList<Appointment> getAppointments() {
        return (ArrayList<Appointment>) appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Observer(){
        appointments = new ArrayList<>();
    }

    @Override
    public void update(Appointment appointment) {}

    public int getId() {
        return id;
    }

    public void setId(int observerID) {
        this.id = observerID;
    }
}
