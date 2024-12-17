package com.secmes.secure_message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secmes.secure_message.service.RSAService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private RSAService rsaService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message, @RequestParam String receiverUsername) {
        // Kullanıcı bilgilerini ve receiver'ın public anahtarını al
        // RSA şifreleme yap ve veritabanına kaydet
        return "Message sent!";
    }

    @GetMapping("/receive")
    public String receiveMessage() {
        return "Your decrypted messages";
    }
}