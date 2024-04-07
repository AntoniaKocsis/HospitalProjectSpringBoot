package HospitalProject.Controller.Domain.Billing;

public class HospitalBillingSystem {
    private static HospitalBillingSystem instance;

    private HospitalBillingSystem() {
    }

    public static HospitalBillingSystem getInstance() {
        if (instance == null) {
            instance = new HospitalBillingSystem();
        }
        return instance;
    }
    public double patientAppointmentBill(){
        Billing billingWithConsultation = new ConsultationFeeDecorator(new BasicBilling());
        return billingWithConsultation.calculateCost();
    }
    public double patientTestsBill(){
        Billing billingWithTest = new TestFeeDecorator(new BasicBilling());
        return billingWithTest.calculateCost();
    }
}
