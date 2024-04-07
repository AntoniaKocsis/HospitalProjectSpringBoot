package HospitalProject.Controller.Domain.Billing;

public class TestFeeDecorator extends BillingDecorator {
    public TestFeeDecorator(Billing decoratedBilling) {
        super(decoratedBilling);
    }

    @Override
    public double calculateCost() {
        double basicCost = super.calculateCost();
        double testFee = 30.0;
        return basicCost + testFee;
    }
}