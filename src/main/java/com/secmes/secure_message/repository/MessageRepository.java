package com.secmes.secure_message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secmes.secure_message.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
