package com.secmes.secure_message.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password; // Şifrelenmiş formatta saklanacak

    @Lob
    private byte[] publicKey; // Kullanıcının RSA public anahtarı
}
