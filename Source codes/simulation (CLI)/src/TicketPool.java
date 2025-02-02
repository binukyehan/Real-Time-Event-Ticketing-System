import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private int maximumTicketCapacity;
    private int totalTickets;
    private int totalTicketsAdded = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final List<String> tickets = Collections.synchronizedList(new ArrayList<>());;

    public TicketPool(int maximumTicketCapacity, int totalTickets) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.totalTickets = totalTickets;

    }

    public void addTicket(){
        lock.lock();
        try{
            while(tickets.size() >= totalTickets){
                notFull.await();//wait if the pool is full
            }

            tickets.add("1");
            totalTicketsAdded++;
            System.out.println("1 Ticket added. Available tickets in the ticket pool: " + tickets.size() + ", Total tickets added : " + totalTicketsAdded);
            Main.logger.info("1 Ticket added. Available tickets in the ticket pool: " + tickets.size() + ", Total tickets added : " + totalTicketsAdded);
            notEmpty.signalAll();

        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void removeTicket(){
        lock.lock();
        try{
            while (tickets.isEmpty()) {
                notEmpty.await(); //wait if the ticket pool is empty
            }
            tickets.remove(0);
            System.out.println("1 Ticket purchased by "+ Thread.currentThread().getName() + ". Tickets remaining in the pool: " + tickets.size());
            Main.logger.info("1 Ticket purchased by "+ Thread.currentThread().getName() + ". Tickets remaining in the pool: " + tickets.size());

            if (tickets.isEmpty() && totalTicketsAdded >= maximumTicketCapacity){
                System.out.print("Enter command (start/stop) : ");
            }
            notFull.signalAll();

        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public boolean isMaxTicketCapacityMet(){
        return totalTicketsAdded >= maximumTicketCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTotalTicketsAdded() {
        return totalTicketsAdded;
    }

    public void setTotalTicketsAdded(int totalTicketsAdded) {
        this.totalTicketsAdded = totalTicketsAdded;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public void setMaximumTicketCapacity(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }
}
