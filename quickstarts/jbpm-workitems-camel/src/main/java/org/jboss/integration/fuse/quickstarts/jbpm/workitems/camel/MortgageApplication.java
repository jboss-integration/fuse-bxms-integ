package org.jboss.integration.fuse.quickstarts.jbpm.workitems.camel;

public class MortgageApplication {
    
    private int id;
    private int income; // applicants income
    private int ammount; // the ammount of mortgage
    private boolean accepted; // is application accepted

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
