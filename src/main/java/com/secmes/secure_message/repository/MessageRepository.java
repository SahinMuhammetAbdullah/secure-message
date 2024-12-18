package com.secmes.secure_message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secmes.secure_message.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiverUsername(String receiverUsername);

    List<Message> findAllBySenderUsername(String username);
}
