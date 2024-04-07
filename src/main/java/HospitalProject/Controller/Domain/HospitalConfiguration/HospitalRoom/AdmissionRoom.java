package HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom;

import HospitalProject.Controller.Domain.HospitalServices.Admissions.Admission;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Admission")
public class AdmissionRoom extends HospitalRoom {
    @OneToMany(mappedBy = "admissionRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Admission> admissions;

    public AdmissionRoom() {admissions = new ArrayList<>();}

    public AdmissionRoom(int roomNumber) {
        super(roomNumber);
        this.setAvailable(true);
        admissions = new ArrayList<>();
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }
    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}
