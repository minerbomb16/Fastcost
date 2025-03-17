# FastCost - Desktop Cost Estimation Tool

FastCost is a **JavaFX-based** desktop application designed for creating and managing cost estimates. It allows users to create, save, and open cost estimate files, generate PDF reports, and interact with a database. Additionally, a C# launcher is included to enable file association for `.fcmb` files, allowing users to open them via the "Open with" option.

## Technologies Used
- **JavaFX** – GUI framework for building the desktop interface
- **Maven** – Dependency management
- **PDF Generation** – Automated PDF report creation
- **Custom File Handling (`.fcmb`)** – Saving and loading cost estimate files
- **C# Launcher** – `.exe` starter for file association support

## Features

### 1. File Management
- Save and open cost estimate files (`.fcmb` format)
- Custom file reader and writer for structured cost data
- Integration with a C# starter for quick file opening

### 2. PDF Report Generation
- Export cost estimates as **formatted PDF reports**
- Ensures structured and professional document output

### 3. Database Integration
- Uses **User's EXCEL file** as the database

### 4. User Interface
- Developed with **JavaFX**, providing a modern and interactive UI
- Supports **editable tables**
- Provides alerts and notifications for better user experience
- Filtered list functionality: Users can search and select elements from a predefined list. Upon selection, the ID, name, and price are automatically inserted into the form.

## Images

### Empty table
![EmptyMenu](https://raw.githubusercontent.com/minerbomb16/Pngs/refs/heads/main/FastCost/EmptyMenu.png)

### Table
![Menu](https://raw.githubusercontent.com/minerbomb16/Pngs/refs/heads/main/FastCost/Menu.png)

### PDF file
![Pdf](https://raw.githubusercontent.com/minerbomb16/Pngs/refs/heads/main/FastCost/Pdf.png)