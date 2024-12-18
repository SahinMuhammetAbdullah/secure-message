package com.secmes.secure_message.service;

import com.secmes.secure_message.model.User;
import com.secmes.secure_message.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password) throws NoSuchAlgorithmException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048, SecureRandom.getInstanceStrong());
        KeyPair keyPair = keyGen.generateKeyPair();

        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
    
        user.setPublicKey(publicKey);
        user.setPrivateKey(privateKey);
        user.setCreatedDate(sdf.format(new java.util.Date()));
    
        return userRepository.save(user);
    }
}
