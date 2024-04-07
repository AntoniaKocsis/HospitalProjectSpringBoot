package HospitalProject.Controller.Domain.HospitalServices.LabTestResult;

import HospitalProject.Controller.Domain.Patient.Patient;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lab_test_result")
public class LabTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;
    private String result;
    private String testName;
    public LabTestResult(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTestName() {
        return testName;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public static class LabTestResultBuilder {
        private Patient patient;
        private String testName;
        private LocalDateTime date;
        private String result;

        public LabTestResultBuilder withPatient(Patient patient) {
            this.patient = patient;
            return this;
        }

        public LabTestResultBuilder withTestName(String testName) {
            this.testName = testName;
            return this;
        }

        public LabTestResultBuilder withTestDate(LocalDateTime testDate) {
            this.date = testDate;
            return this;
        }

        public LabTestResultBuilder withResult(String result) {
            this.result = result;
            return this;
        }

        public LabTestResult build() {
            LabTestResult labTestResult = new LabTestResult();
            labTestResult.patient = this.patient;
            labTestResult.testName = this.testName;
            labTestResult.date = this.date;
            labTestResult.result = this.result;
            return labTestResult;
        }
    }

}
