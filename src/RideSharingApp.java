interface FareStrategy {
    double calculateFare(double distance, double duration);
}

class RegularFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(double distance, double duration) {
        return distance * 1.00 + duration * 0.25;
    }
}

class PremiumFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(double distance, double duration) {
        return distance * 2.00 + duration * 0.50;
    }
}

class DiscountFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(double distance, double duration) {
        return distance * 0.80 + duration * 0.20;
    }
}

class SurgeFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(double distance, double duration) {
        return (distance * 2.00 + duration * 0.50) * 2;
    }
}

class RideContext {
    private FareStrategy fareStrategy;
    private static final double MINIMUM_CHARGE = 5.00;

    public void setFareStrategy(FareStrategy fareStrategy) {
        this.fareStrategy = fareStrategy;
    }

    public double calculateFare(double distance, double duration) {
        if (distance < 0 || duration < 0) {
            throw new IllegalArgumentException("Distance or duration cannot be negative");
        }
        double fare = fareStrategy.calculateFare(distance, duration);
        return Math.max(fare, MINIMUM_CHARGE);
    }

    public void selectStrategyBasedOnConditions(double distance, double duration, boolean isPeakHour, boolean isPremiumUser) {
        if (isPeakHour) {
            setFareStrategy(new SurgeFareStrategy());
        } else if (isPremiumUser) {
            setFareStrategy(new PremiumFareStrategy());
        } else if (distance > 20) {
            setFareStrategy(new DiscountFareStrategy());
        } else {
            setFareStrategy(new RegularFareStrategy());
        }
    }
}

public class RideSharingApp {
    public static void main(String[] args) {
        RideContext rideContext = new RideContext();

        rideContext.selectStrategyBasedOnConditions(10, 15, false, false);
        System.out.println("Regular Fare: " + rideContext.calculateFare(10, 15));

        rideContext.selectStrategyBasedOnConditions(10, 15, true, false);
        System.out.println("Surge Fare: " + rideContext.calculateFare(10, 15));

        rideContext.selectStrategyBasedOnConditions(25, 30, false, false);
        System.out.println("Discount Fare: " + rideContext.calculateFare(25, 30));

        rideContext.selectStrategyBasedOnConditions(10, 15, false, true);
        System.out.println("Premium Fare: " + rideContext.calculateFare(10, 15));
    }
}
