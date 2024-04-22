public class Main {
    public static void main(String[] args) {
        // Create a barber shop with 3 chairs and a named barber
        BlockingQueueBarberShop shop = new BlockingQueueBarberShop(3, "Barber Joe");

        // Create and start some customers
        Thread[] customers = new Thread[5];
        for (int i = 0; i < customers.length; i++) {
            Customer c = new Customer(shop, "Customer " + (i + 1));
            customers[i] = new Thread(c);
            customers[i].start();
        }

        // Start the barber thread (optional, depending on how you want to manage barber activity)
        Thread barberThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    shop.startHaircut(); // Assume the barber checks for customers periodically
                    Thread.sleep(1000); // Simulate time taken for haircut or waiting for customers
                }
            } catch (InterruptedException e) {
                System.out.println("Barber's day is over");
            }
        });
        barberThread.start();

        // Wait for all customers to finish
        for (Thread c : customers) {
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Stop the barber thread after all customers are done
        barberThread.interrupt();
        try {
            barberThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The barber shop is closed for the day.");
    }
}
