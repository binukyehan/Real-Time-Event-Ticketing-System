# Event Ticketing System

Event Ticketing System is a real time ticket distribution and buying system to manage ticket pools efficiently. It has a multi threaded backend written in Java and a GUI written with React and Tailwind CSS for better user interaction. It supports dynamic ticket allocation and real time updates for vendors and customers.

---

## Features

- **Real-time Ticket Management**: Updates ticket availability automatically when tickets are released or bought.
- **Multi-threaded Processing**: Allows for smooth concurrent ticket release and purchase requests using the producer consumer pattern.
- **Interactive GUI**: It provides users to check ticket availability, make purchase and interact with the system visually.
- **Secure Operations**: Input validation to avoid misuse of the system.

---

## Setup Instructions

### Prerequisites

Ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 17 or higher.
- **Node.js and npm**: Latest stable versions.
- **MySQL**: For database operations

---

### Backend Setup (CLI)

1. Clone the repository:

```bash
git clone <repository-url>
cd event-ticketing-system
```

2. Compile the Java source code:

```bash
javac -d bin src/*.java
```

3. Run the main program:

```bash
java -cp bin Main
```

4. Configure system parameters: The CLI will prompt you to set:

```
totalTickets
ticketReleaseRate
customerRetrievalRate
maxTicketCapacity
```

5. Use CLI Commands:

```
start: Vendors and Customers will begin to add and purchase tickets from the ticketpool
stop: Stops all the operations.
```

---

### Frontend Setup (GUI)

1. Navigate to the frontend directory:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
npm run dev
```

### XAMPP setup and database configuration

1. Download and install XAMPP

2. Start XAMPP services

3. Create the database

- Open phpMyAdmin in your browser.
- Create a new database with the name **event_ticketing_system**.

4. Configure database connection in the application

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_ticketing_system
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

---

## Usage

### CLI Instructions

- When the program is set to run you can interact with the system through CLI commands.
- Example: Start the system with predefined settings and monitor transactions of the ticket pool.

### GUI Instructions

**1. For Vendors**

> - Launch the front end and open the web application.
> - Log in or Register to continue to the ticket issuing dashboard.
> - In the Issue Tickets page the currently available amount of tickets is shown in real-time for each event.
> - After entering the username, event ID, and the number of tickets, the vendor can add tickets to the relevant event (ticket pool)

**2. For Customers**

> - Launch the front end and open the web application.
> - Log in or Register to continue to the ticket purchasing dashboard.
> - On the Purchase Tickets page the currently available amount of tickets is shown in real-time for each event.
> - After entering the username, event ID, and the number of tickets, the customer can purchase tickets from the relevant event (ticket pool)

**2. For Admins**

> - Launch the front end and open the web application.
> - After clicking the "admin" button, they will be redirected to a page with admin functions as "Control events", "Create event", and "Delete event".
> - Admin can choose the relevant function and proceed to do their task.

---

## Troubleshooting

### 1. Backend Issues:

- Ensure Java is installed and properly configured in your system PATH.
- Check for compilation errors in the source code.

### 2. Frontend Issues:

- Ensure Node.js and npm are installed correctly.
- Clear the cache and reinstall dependencies if the server doesn't start:

```bash
npm cache clean --force
npm install
```

### 3. Database Connectivity:

- Verify that MySQL is running and the database configuration matches in the code.

---

## Future Enhancements

- Add real-time notifications for event status updates.
- Implement an analytics dashboard for vendors and customers.
- Enhance security with JWT authentication.

## Acknowledgments

This project was developed as part of the Object-Oriented Programming coursework. Special thanks to the module team for their collaborative effort and dedication.

## Contact

For any queries, please contact:

> - Name - **Binuk Dias**
> - Email - ***binuk20221411@iit.ac.lk***
