package week7;

interface StaffMember {
    String getFullName();
    double getAnnualSalary();
}

class ContractorRecord {
    public String getFirstName() {
        return "John";
    }

    public String getLastName() {
        return "Doe";
    }

    public double getMonthlyPay() {
        return 4000.0;
    }
}

class ContractorAdapter implements StaffMember {
    private ContractorRecord record;

    public ContractorAdapter(ContractorRecord record) {
        this.record = record;
    }

    public String getFullName() {
        return record.getFirstName() + " " + record.getLastName();
    }

    public double getAnnualSalary() {
        return record.getMonthlyPay() * 12.0;
    }
}
