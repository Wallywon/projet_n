# projet_n
Projet cours de Système d'informations - M1 UBO



###############################################################################################
# Student Data Parser API

A simple web application that parses student data from Excel files and stores it in a database.

## Prerequisites

- Java 11 or higher
- Apache Tomcat 10.0.x
- Eclipse IDE for Java EE

## Project Setup

1. Clone or download the project
2. Import as existing Maven project in Eclipse
3. Ensure all Maven dependencies are downloaded

## Running the Application

1. Right-click on the project → Run As → Run on Server
2. Select Apache Tomcat 10.0 server
3. Access the application at: http://localhost:8080/parser_api/

## Testing

1. Create a simple Excel file with these columns:
   - First Name
   - Last Name
   - Student Number
   - Birth Date

2. Use the web interface to:
   - Upload the Excel file using the form
   - View parsed students by clicking "View All Students"

## Implementation Notes

- The application uses in-memory storage by default
- No database configuration is required for testing
- Student data is lost when the server is restarted
- No need to save in long time because at the end we will use another app (Student-api) to CRUD students

##################################################################################
