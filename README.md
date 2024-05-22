# Time-Based One-Time Password (TOTP) Authentication Console Application

## Overview
This Time-Based One-Time Password (TOTP) Authentication System securely manages authentication requests between a client and a server using time-based, one-time passwords. It ensures each authentication session is both time-sensitive and secure, providing strong resistance to replay attacks.

## Components
The system includes two main components:
- **Server**: Manages incoming authentication requests and validates TOTPs.
- **Client**: Sends authentication data (username and TOTP) to the server.

## Detailed Component Description

### Server
- **Listening for Connections**: Opens a server socket on a configured port to listen for incoming client connections.
- **Generating and Validating TOTPs**: Generates a TOTP from a shared secret and compares it with the client's TOTP.
- **Handling Client Requests**: Reads the username and TOTP sent by the client, checks the TOTP validity, and responds accordingly.
- **Logging**: Logs the outcome of authentication attempts to `server_log.txt`.

### Client
- **Connecting to the Server**: Establishes a socket connection to the server using specified host and port settings.
- **User Interaction**: Prompts the user to enter a username and TOTP.
- **Sending Data to Server**: Sends the username and TOTP to the server for authentication.
- **Receiving Server Response**: Displays the server's authentication result.
- **Logging**: Records the details of each authentication attempt in `client_log.txt`.

## Prerequisites
- Java Development Kit (JDK) 11 or higher
- Modern IDE such as IntelliJ IDEA, Eclipse, or Visual Studio Code

## Configuration
Ensure the `common.Config` class is set up correctly:
- `SERVER_PORT`: Port number for the server.
- `SERVER_HOST`: Server address, typically `localhost`.
- `SECRET`: Shared secret key for TOTP generation.

## Running the Application

### Setting Up
1. Clone or download the project repository.
2. Open the project in your preferred IDE.



### Examples of Execution

![Screenshot 2024-05-22 233738](https://github.com/gertahodolli/Time-Based-One-Time-Password-Authentication/assets/147100017/e4e3f3bb-1f69-4b55-ad10-04aac3fbfd4f)

### Compiling the Java Files
Navigate to the project directory and compile:
```bash
javac server/*.java common/*.java
javac client/*.java common/*.java



