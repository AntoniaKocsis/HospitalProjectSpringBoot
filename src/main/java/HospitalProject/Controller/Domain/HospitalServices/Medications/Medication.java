package HospitalProject.Controller.Domain.HospitalServices.Medications;

import HospitalProject.Controller.Domain.HospitalServices.Prescriptions.Prescription;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int concentration;
    private String name;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, mappedBy = "medications")
    private List<Prescription> prescriptions;
    public Medication(String name, int concentration) {
        this.name = name;
        this.concentration = concentration;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Medication() {

    }


    public int getConcentration() {
        return concentration;
    }

    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "medicationID=" + id +
                ", concentration=" + concentration +
                ", name='" + name + '\'' +
                '}';
    }
}
