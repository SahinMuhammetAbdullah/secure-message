package com.secmes.secure_message.controller;

import java.security.PublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secmes.secure_message.model.Message;
import com.secmes.secure_message.model.User;
import com.secmes.secure_message.repository.MessageRepository;
import com.secmes.secure_message.repository.UserRepository;
import com.secmes.secure_message.service.RSAService;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RSAService rsaService;

    @GetMapping("/inbox")
    public String getInbox(Model model) {
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "inbox";
    }

    @GetMapping("/send")
    public String showSendMessagePage() {
        return "sendMessage";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String senderUsername, @RequestParam String receiverUsername, @RequestParam String content) throws Exception {
        User sender = userRepository.findByUsername(senderUsername).orElseThrow();
        User receiver = userRepository.findByUsername(receiverUsername).orElseThrow();

        // Mesajı RSA ile şifreleme
        PublicKey receiverPublicKey = rsaService.getPublicKeyFromBytes(receiver.getPublicKey());
        byte[] encryptedMessage = rsaService.encryptMessage(content, receiverPublicKey);

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setEncryptedMessage(encryptedMessage);
        messageRepository.save(message);

        return "redirect:/messages/inbox";
    }
}
