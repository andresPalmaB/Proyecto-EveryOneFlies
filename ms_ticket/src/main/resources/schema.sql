DROP TABLE IF EXISTS Estudiante;
CREATE TABLE Tickets (
   idtickets INT PRIMARY KEY,
   flightInfo VARCHAR(100) NOT NULL,
   idPassenger VARCHAR(150) UNIQUE,
   issueDate DATETIME,
   ticketStatus VARCHAR(100) NOT NULL
);