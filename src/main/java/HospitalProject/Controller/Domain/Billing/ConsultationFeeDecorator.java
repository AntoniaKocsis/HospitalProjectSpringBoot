package HospitalProject.Controller.Domain.Billing;

public class ConsultationFeeDecorator extends BillingDecorator {
    public ConsultationFeeDecorator(Billing decoratedBilling) {
        super(decoratedBilling);
    }

    @Override
    public double calculateCost() {
        // Additional feature: Add consultation fee
        double basicCost = super.calculateCost();
        double consultationFee = 50.0;
        return basicCost + consultationFee;
    }
}
