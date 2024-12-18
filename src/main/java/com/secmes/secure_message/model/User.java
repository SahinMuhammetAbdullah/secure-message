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

    @Column(nullable = false) // Boş olamaz
    private String password; // Şifrelenmiş formatta saklanacak

    @Lob
    private byte[] publicKey; // Kullanıcının RSA public anahtarı

    // İsteğe bağlı olarak, şifrelenmiş özel anahtar saklama
    @Lob
    private byte[] privateKey; // Kullanıcının RSA private anahtarı (isteğe bağlı)

    // Kullanıcının kaydolma tarihi
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdDate;

    // Kullanıcı durumu (Aktif/Pasif gibi)
    @Column(nullable = false)
    private boolean active = true; // Varsayılan olarak aktif
}
