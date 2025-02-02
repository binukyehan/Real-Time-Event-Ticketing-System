public class Vendor implements Runnable{
    private int ticketReleaseRate;
    private TicketPool ticketPool;
    private boolean running = true;

    public Vendor(int ticketReleaseRate, TicketPool ticketPool) {
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (running){
            if(ticketPool.isMaxTicketCapacityMet()){
                System.out.println("\nVendor stopped adding tickets. Max event capacity reached.");
                break;
            }
            ticketPool.addTicket();
            try {
                Thread.sleep(10000/ticketReleaseRate); //tickets per 10 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop(){
        running = false;
    }
}
