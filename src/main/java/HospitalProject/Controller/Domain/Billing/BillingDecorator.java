package HospitalProject.Controller.Domain.Billing;

public abstract class BillingDecorator implements Billing {
    protected Billing decoratedBilling;

    public BillingDecorator(Billing decoratedBilling) {
        this.decoratedBilling = decoratedBilling;
    }

    @Override
    public double calculateCost() {
        return decoratedBilling.calculateCost();
    }
}
