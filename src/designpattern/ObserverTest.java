package designpattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface Observer {
    public void update(float interest);
}

interface Subject {
    public void registerObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObservers();
}

class Loan implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String type;
    private float interest;
    private String bank;

    public Loan(String type, float interest, String bank) {
        this.type = type;
        this.interest = interest;
        this.bank = bank;
    }

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
        notifyObservers();
    }

    public String getBank() {
        return this.bank;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer ob : observers) {
            System.out.println("Notifying Observers on change in Loan interest rate");
            ob.update(this.interest);
        }
    }

}

class Newspaper implements Observer {
    @Override
    public void update(float interest) {
        System.out.println("Newspaper: Interest Rate updated, new Rate is: " + interest);
    }
}

class Internet implements Observer {
    @Override
    public void update(float interest) {
        System.out.println("Internet: Interest Rate updated, new Rate is: " + interest);
    }
}

public class ObserverTest {

    public static void main(String args[]) throws Exception {
        // this will maintain all loans information
        Newspaper printMedia = new Newspaper();
        Internet onlineMedia = new Internet();

        Loan personalLoan = new Loan("Personal Loan", 12.5f, "Standard Charterd");
        personalLoan.registerObserver(printMedia);
        personalLoan.registerObserver(onlineMedia);
        Random rand = new Random(System.currentTimeMillis());
        int cnt = 10;
        while (cnt-- > 0) {
            Thread.sleep(1000);
            personalLoan.setInterest((float) rand.nextInt(150) / 10);
        }
    }
}
