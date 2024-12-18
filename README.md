# Secure Message Application

## Summary

The Secure Message Application is a web-based platform that allows users to send and receive encrypted messages securely. The application uses RSA encryption to ensure that messages are only readable by the intended recipient. The application is built using Spring Boot, Thymeleaf, and Bootstrap for the frontend.

## Key Features

- User registration and authentication
- Sending and receiving encrypted messages
- Viewing sent and received messages
- Secure storage of user keys

## How User Keys Are Stored

User keys are generated using RSA encryption. Each user has a unique pair of public and private keys. The keys are stored in the database as byte arrays. The public key is used to encrypt messages, while the private key is used to decrypt messages.

The keys are generated and stored as follows:

1. When a user registers, a new RSA key pair is generated.
2. The public and private keys are encoded and stored in the database.
3. The public key is used to encrypt messages sent to the user.
4. The private key is used to decrypt messages received by the user.

## How Messages Are Encrypted

Messages are encrypted using the recipient's public key. The encryption process ensures that only the recipient can decrypt and read the message using their private key.

The encryption process is as follows:

1. The sender writes a message and selects a recipient.
2. The application retrieves the recipient's public key from the database.
3. The message is encrypted using the recipient's public key.
4. The encrypted message is stored in the database and sent to the recipient.

## How Messages Are Decrypted

Messages are decrypted using the recipient's private key. The decryption process ensures that only the recipient can read the message.

The decryption process is as follows:

1. The recipient logs in to the application.
2. The application retrieves the recipient's private key from the database.
3. The encrypted message is retrieved from the database.
4. The message is decrypted using the recipient's private key and displayed to the recipient.

## Technologies Used

- Spring Boot
- Thymeleaf
- Bootstrap
- RSA Encryption

## Getting Started

To get started with the Secure Message Application, follow these steps:

1. Clone the repository.
2. Set up the database and configure the connection in [`src/main/resources/application.properties`](src/main/resources/application.properties).
3. Run the application using `mvn spring-boot:run`.
4. Access the application at [`http://localhost:8080`](http://localhost:8080).

For more detailed instructions, refer to the documentation in the repository.

## License

This project is licensed under the MIT License. See the LICENSE file for details.