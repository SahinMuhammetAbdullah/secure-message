package com.secmes.secure_message.controller;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

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

    @GetMapping("/")
    public String redirectInbox() {
        return "redirect:/messages/inbox";
    }

    @GetMapping("/inbox")
    public String getInbox(Model model) {
        // Get the logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Get all messages for the logged-in user
        List<Message> messages = messageRepository.findAllByReceiverUsername(username);
        List<String> decryptedMessages = new ArrayList<>();

        for (Message message : messages) {
            try {
                String decryptedMessage = rsaService.decryptMessage(
                        Base64.getEncoder().encodeToString(user.getPrivateKey()),
                        new String(message.getEncryptedMessage()));
                System.out.println("decrypted message: " + decryptedMessage);
                decryptedMessages.add(decryptedMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("messages", messages);
        model.addAttribute("decryptedMessages", decryptedMessages);
        model.addAttribute("username", username);
        return "inbox";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String receiverUsername, @RequestParam String content) throws Exception {
        // Get the logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = authentication.getName();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");

        // Get sender and receiver users from the database
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Sender not found"));
        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Receiver not found"));

        // Encrypt the message with RSA
        PublicKey receiverPublicKey = rsaService.getPublicKeyFromBytes(receiver.getPublicKey());
        String encryptedMessage = rsaService.encryptMessage(receiverPublicKey, content);

        // Create and save the message
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setEncryptedMessage(encryptedMessage);
        message.setDateTime(formatter.format(new Date()));
        messageRepository.save(message);

        return "redirect:/messages/inbox";
    }

    @GetMapping("/send")
    public String sendPage(Model model) {
        List<User> users = userRepository.findAll();
        users.removeIf(
                user -> user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("users", users);
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());

        return "sendMessage";
    }

    @GetMapping("/delete/{id}")
    public String deleteMessage(@PathVariable Long id, HttpServletRequest request) {
        messageRepository.deleteById(id);
        String referer = request.getHeader("Referer");

        if (referer != null) {
            if (referer.contains("/messages/inbox")) {
                return "redirect:/messages/inbox";
            } else if (referer.contains("/messages/sent")) {
                return "redirect:/messages/sent";
            }
        }

        // Default redirect if referer is not recognized
        return "redirect:/messages/inbox"; // or another default page
    }

    @GetMapping("/sent")
    public String getSentBox(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Message> messages = messageRepository.findAllBySenderUsername(username);
        List<String> decryptedMessages = new ArrayList<>();

        for (Message message : messages) {
            try {
                String decryptedMessage = rsaService.decryptMessage(
                        Base64.getEncoder().encodeToString(message.getReceiver().getPrivateKey()),
                        new String(message.getEncryptedMessage()));
                decryptedMessages.add(decryptedMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("messages", messages);
        model.addAttribute("decryptedMessages", decryptedMessages);
        model.addAttribute("username", username);
        return "sent";
    }
}