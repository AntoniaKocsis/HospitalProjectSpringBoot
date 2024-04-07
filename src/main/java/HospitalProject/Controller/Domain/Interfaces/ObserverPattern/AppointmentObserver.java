package HospitalProject.Controller.Domain.Interfaces.ObserverPattern;

import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;

public interface AppointmentObserver {
    void update(Appointment appointment);
}
