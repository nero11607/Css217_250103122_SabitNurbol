package week7;
interface Membership {
    String getPerks();
    double getMonthlyFee();
}

class BasicGymAccess implements Membership {
    public String getPerks() {
        return "Gym Floor";
    }

    public double getMonthlyFee() {
        return 1000.0;
    }
}

class PoolAccessDecorator implements Membership {
    private Membership membership;

    public PoolAccessDecorator(Membership membership) {
        this.membership = membership;
    }

    public String getPerks() {
        return membership.getPerks() + ", Pool Access";
    }

    public double getMonthlyFee() {
        return membership.getMonthlyFee() + 400.0;
    }
}

class SaunaDecorator implements Membership {
    private Membership membership;

    public SaunaDecorator(Membership membership) {
        this.membership = membership;
    }

    public String getPerks() {
        return membership.getPerks() + ", Sauna";
    }

    public double getMonthlyFee() {
        return membership.getMonthlyFee() + 250.0;
    }
}

class PersonalTrainerDecorator implements Membership {
    private Membership membership;

    public PersonalTrainerDecorator(Membership membership) {
        this.membership = membership;
    }

    public String getPerks() {
        return membership.getPerks() + ", Personal Trainer";
    }

    public double getMonthlyFee() {
        return membership.getMonthlyFee() + 1500.0;
    }
}
