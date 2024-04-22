import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueBarberShop {
    private int numberOfChairs;
    private BlockingQueue<Customer> waitingCustomers;
    private Barber barber;

    public BlockingQueueBarberShop(int numberOfChairs, String barberName) {
        this.numberOfChairs = numberOfChairs;
        this.waitingCustomers = new LinkedBlockingQueue<>(numberOfChairs);
        this.barber = new Barber(barberName);
    }

    public void takeASeat(Customer customer) throws InterruptedException {
        if (waitingCustomers.remainingCapacity() == 0) {
            System.out.println(customer.getName() + " leaves the shop, no chairs available");
            return;
        }
        waitingCustomers.put(customer);
        System.out.println(customer.getName() + " takes a seat. " + waitingCustomers.size() + " customers waiting.");
    }

    public void startHaircut() throws InterruptedException {
        if (waitingCustomers.isEmpty()) {
            System.out.println("Barber " + barber.getName() + " is sleeping.");
        } else {
            Customer customer = waitingCustomers.take();
            System.out.println("Barber " + barber.getName() + " starts haircut for " + customer.getName() + ". " + waitingCustomers.size() + " customers waiting.");
        }
    }

    public boolean endHaircut(Customer customer) {
        System.out.println("Barber " + barber.getName() + " ends haircut for " + customer.getName() + ".");
        return true;
    }

    public boolean isShopFull() {
        return waitingCustomers.remainingCapacity() == 0;
    }

    public boolean requestHaircut(Customer customer) {
        return false;
    }

   
}