import java.util.Random;

/**
 * Represents a customer in a barber shop
 */
public class Customer implements Runnable {
    private static final int MAX_WAIT_TIME = 5000; // maximum time to wait before leaving the shop
    private static int next_id = 0;

    private final int id;
    private final BlockingQueueBarberShop shop;
    private final Random rand;
    private String name;

    /**
     * Create a new Customer object
     * @param barber the BarberShop object associated with this customer
     */
    public Customer(BlockingQueueBarberShop barber, String name) {
        this.id = next_id++;
        this.shop = barber;
        this.name = name;
        this.rand = new Random();
    }

    /**
     * Run method for the customer thread. The customer will enter the shop, check if there's a free chair in the
     * waiting room, and if there is, sit down and wait for a haircut. If all chairs are taken, the customer will
     * leave the shop. If there's a free barber chair, the customer will wake up the barber and request a haircut.
     */
    @Override
    public void run() {
        try {
            System.out.println(this + " enters the shop");

            // check if the shop is too full
            if (shop.isShopFull()) {
                System.out.println(this + " sees that the shop is too full and leaves");
                return;
            }

            // check if there's a free barber chair
            boolean hasHaircut = shop.endHaircut(this);
            if (!hasHaircut) {
                // no free chair, customer will wait
                System.out.println(this + " is waiting in the waiting room");
                shop.takeASeat(this);
                hasHaircut = shop.endHaircut(this);
            }

            // got a haircut, leave the shop
            if (hasHaircut) {
                System.out.println(this + " got a haircut and is leaving the shop");
            } else {
                // waited too long, leave the shop
                System.out.println(this + " got tired of waiting and is leaving the shop");
            }

        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
            e.printStackTrace();
        }
    }

    /**
     * Returns whether this customer wants a haircut
     * @return true if this customer wants a haircut, false otherwise
     */
    public boolean wantsHaircut() {
        return rand.nextBoolean();
    }

    /**
     * Returns a string representation of this customer
     * @return a string representation of this customer
     */
    @Override
    public String toString() {
        return "Customer " + id;
    }

    public String getName() {
        return name;
    }

    public String getname(){
        return name;
    }
}
