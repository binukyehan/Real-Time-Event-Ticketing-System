public class Customer implements Runnable{
    private TicketPool ticketPool;
    private int customerRetrievalRate;
    private String customerId;
    private boolean running = true;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, String customerId) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(customerId);
        while (running){
            ticketPool.removeTicket();
            try {
                Thread.sleep(10000/customerRetrievalRate); //tickets per 10 seconds
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop(){
        running = false;
    }
}
