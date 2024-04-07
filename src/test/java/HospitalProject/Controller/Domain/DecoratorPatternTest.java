package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.Billing.*;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.HospitalRoomService;
import HospitalProject.Controller.Domain.HospitalServices.Appointments.Appointment;
import HospitalProject.Controller.Domain.HospitalServices.LabTestResult.LabTestResult;
import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.Patient.PatientRepository;
import HospitalProject.Controller.Domain.Patient.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Map;

@SpringBootTest
@Transactional
@Rollback
public class DecoratorPatternTest {
    @Autowired
    private HospitalBillingSystemService hospitalBillingSystemService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testBillingSystem() throws PatientNotFoundException {
        BasicBilling basicBilling = new BasicBilling();
        ConsultationFeeDecorator consultationFeeDecorator = new ConsultationFeeDecorator(basicBilling);
        TestFeeDecorator testFeeDecorator = new TestFeeDecorator(basicBilling);
        double hospitalFee = basicBilling.calculateCost();
        assert hospitalFee == 100;
        double consultationFee = consultationFeeDecorator.calculateCost();
        assert consultationFee == hospitalFee + 50;
        double testFee = testFeeDecorator.calculateCost();
        assert testFee == hospitalFee + 30;

        Patient patient = patientService.get(1);

        Map<LabTestResult,Double> testBills = hospitalBillingSystemService.listPatientTestBills(patient.getId());
        for(Double value:testBills.values())
            assert value == 130;
        Map<Appointment,Double> appointmentBills = hospitalBillingSystemService.listPatientAppointmentBills(patient.getId());
        for(Double value:appointmentBills.values())
            assert value == 150;

    }

}
