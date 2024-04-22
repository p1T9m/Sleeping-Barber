import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShop {
    private int numberOfChairs;
    private int waitingCustomers;
    private Lock lock;
    private Condition barberAvailable;
    private Condition customerAvailable;

    public BarberShop(int numberOfChairs) {
        this.numberOfChairs = numberOfChairs;
        this.waitingCustomers = 0;
        this.lock = new ReentrantLock();
        this.barberAvailable = lock.newCondition();
        this.customerAvailable = lock.newCondition();
    }

    public void takeASeat(Customer customer) throws InterruptedException {
        lock.lock();
        try {
            if (waitingCustomers == numberOfChairs) {
                System.out.println(((Customer) customer).getName() + " leaves the shop, no chairs available");
                return;
            }
            waitingCustomers++;
            System.out.println(customer.getName() + " takes a seat. " + waitingCustomers + " customers waiting.");
            customerAvailable.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void startHaircut(BarberShop barber) throws InterruptedException {
        lock.lock();
        try {
            while (waitingCustomers == 0) {
                System.out.println("Barber " + BarberShop.getName() + " is sleeping.");
                barberAvailable.await();
            }
            waitingCustomers--;
            System.out.println("Barber " + BarberShop.getName() + " starts haircut. " + waitingCustomers + " customers waiting.");
        } finally {
            lock.unlock();
        }
    }

    public boolean endHaircut(Customer customer) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Barber " + BarberShop.getName() + " ends haircut.");
            barberAvailable.signal();
        } finally {
            lock.unlock();
        }
        return false;
    }

    static String getName() {
        return null;
    }

    public boolean isShopFull() {
        return false;
    }

   
    
}