
# School Attendance Management System

A Maven-based attendance management system for schools, built using Spring Boot and MySQL. The system tracks student attendance, generates reports, and includes features for notifications and attendance tracking.

## Features

- **Attendance Tracking**: Record daily attendance for students efficiently.
- **Report Generation**: Generate comprehensive attendance reports for classes and individual students.
- **Notifications**: Send notifications regarding attendance status to students and parents.

## Technologies Used

- **Backend**: Spring Boot
- **Database**: MySQL
- **Build Tool**: Maven
- **Frontend**: HTML, CSS, JavaScript

## Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/pyaephyo23/school-attendance-management-system.git
   ```

2. **Navigate to the project directory**:

   ```bash
   cd school-attendance-management-system
   ```

3. **Build the project using Maven**:

   ```bash
   mvn clean install
   ```

4. **Set up the MySQL database**:
   - Create a database named `attendance_db`.
   - Update the database configuration in `application.properties` with your MySQL credentials.

5. **Run the application**:

   ```bash
   mvn spring-boot:run
   ```

6. **Access the application**:
   - Open your browser and navigate to `http://localhost:8080`.

## Usage

- **Admin Panel**: Manage students, classes, and view attendance reports.
- **Teacher Panel**: Mark attendance and view class reports.
- **Student/Parent Portal**: View attendance records and receive notifications.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License.

## Contact

For any inquiries or support, please contact [pyaephyo23](https://github.com/pyaephyo23).
