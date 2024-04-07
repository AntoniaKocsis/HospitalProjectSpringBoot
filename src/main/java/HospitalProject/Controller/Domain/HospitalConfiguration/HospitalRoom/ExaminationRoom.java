package HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom;

import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Examination")
public class ExaminationRoom extends HospitalRoom {

    // Appointment - ExaminationRoom Relationship
    @OneToMany(mappedBy = "examinationRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    public ExaminationRoom() {appointments = new ArrayList<>();}
    public ExaminationRoom(int number) {
        super(number);
        appointments = new ArrayList<>();
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    @Override
    public String toString() {
        return String.valueOf(this.getId()); // Assuming id is a unique identifier for the ExaminationRoom
    }

}
