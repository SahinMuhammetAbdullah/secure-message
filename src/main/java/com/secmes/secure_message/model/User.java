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

    @Column(nullable = false)
    private String password; 

    @Lob
    private byte[] publicKey;

    
    @Lob
    private byte[] privateKey; 

    @Column(nullable = false, updatable = false)
    private String createdDate; 

    @Column(nullable = false)
    private boolean active = true; 
}
