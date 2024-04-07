package HospitalProject.Controller.Domain.Billing;

public class BasicBilling implements Billing {
    @Override
    public double calculateCost() {
        return 100.0;
    }
}
