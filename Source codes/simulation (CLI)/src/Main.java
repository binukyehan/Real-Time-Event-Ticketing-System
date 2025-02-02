import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Main {
    private static int totalTickets;
    private static int maxTicketCapacity;
    private static int ticketReleaseRate;
    private static int customerRetrievalRate;
    private static int noOfCustomers;
    private static TicketPool ticketPool;
    private static Vendor vendor;
    private static Thread vendorThread;
    private static ExecutorService customerExecutor;
    private static boolean isRunning = false;
    public static final Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) {
        try {
            setUpLogger();
            Scanner scanner = new Scanner(System.in);
            //Event Configuration
            System.out.print("\nEnter total number of tickets (Max tickets at a moment in the ticket Pool): ");
            totalTickets = scanner.nextInt();
            inputValidation(totalTickets);

            System.out.print("Enter max ticket capacity (Total for the event): ");
            maxTicketCapacity = scanner.nextInt();
            if (maxTicketCapacity<totalTickets){
                System.out.println("Max ticket capacity should be higher than the total number of tickets");
                main(null);
            }
            inputValidation(maxTicketCapacity);

            System.out.print("Enter vendor ticket release rate (per 10 seconds): ");
            ticketReleaseRate = scanner.nextInt();
            inputValidation(ticketReleaseRate);

            System.out.print("Enter customer retrieval rate (per 10 seconds): ");
            customerRetrievalRate = scanner.nextInt();
            inputValidation(customerRetrievalRate);

            System.out.print("Enter the number of customers : ");
            noOfCustomers = scanner.nextInt();
            inputValidation(noOfCustomers);

            // Log configuration settings
            logger.info("Configuration Settings");
            logger.info("Total Tickets: " + totalTickets);
            logger.info("Max Ticket Capacity: " + maxTicketCapacity);
            logger.info("Vendor Ticket Release Rate: " + ticketReleaseRate);
            logger.info("Customer Retrieval Rate: " + customerRetrievalRate);
            logger.info("Number of Customers: " + noOfCustomers);

            ticketPool = new TicketPool(maxTicketCapacity, totalTickets);

            vendor = new Vendor(ticketReleaseRate, ticketPool);
            vendorThread = new Thread(vendor);

            customerExecutor = Executors.newFixedThreadPool(noOfCustomers);

            userCommands();

        } catch (Exception e){
            System.out.println("Invalid input try again.");
            main(null);
        }
    }

    public static void userCommands(){ //Get user commands (Start/Stop)
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter command (start/stop) : ");
        while (true) {
            String userResponse = scanner.next();
            if (userResponse.equalsIgnoreCase("start")) {
                if (!vendorThread.isAlive() && !isRunning){
                    System.out.println("Event ticketing process started.");
                    logger.info("Event ticketing process started.");
                    vendorThread.start();
                    isRunning =true;
                    for (int i = 1; i <= noOfCustomers; i++) {
                        Customer customer = new Customer(ticketPool, customerRetrievalRate, "Customer " + i);
                        customerExecutor.submit(customer);
                    }

                } else {
                    System.out.println("Ticketing process is already started.");
                    System.out.print("\nEnter command (start/stop) : ");
                }


            } else if (userResponse.equalsIgnoreCase("stop")) {
                if(isRunning) {
                    vendor.stop();
                    isRunning = false;
                    customerExecutor.shutdown();
                    customerExecutor = Executors.newCachedThreadPool();
                    System.out.println("Event ticketing process stopped.");
                    logger.info("Event ticket processing stopped.");
                    while (true) {
                        System.out.print("Do you want to reset the configuration : ");
                        String stopResponse = scanner.next();
                        if (stopResponse.equalsIgnoreCase("yes")) {
                            main(null);
                        } else if (stopResponse.equalsIgnoreCase("no")) {
                            System.out.println("Exiting the Event Ticketing System...");
                            System.exit(0);
                        } else {
                            System.out.println("Enter a valid response.");
                            System.out.print("Do you want to reset the configuration : ");
                        }
                    }
                } else {
                    System.out.println("Event ticketing process has not started.");
                }

            } else {
                System.out.println("Invalid input. Please try again with a valid input.");
                System.out.print("\nEnter command (start/stop) : ");
            }
        }
    }
    private static String inputValidation(int value){ //Validate input values entered by the user
        if (value <= 0){
            System.out.print("Invalid input. Enter positive values only.");
            main(null);
        }
        return null;
    }

    public static void setUpLogger(){ //setting up the logger
        try{
            logger.setLevel(Level.ALL);

            logger.setUseParentHandlers(false);

            FileHandler fileHandler = new FileHandler("event_ticketing_system.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e){
            System.err.println("Failed to set up logger: " + e.getMessage());
        }
    }

}