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
import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password) throws NoSuchAlgorithmException {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss");
    
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Şifreyi güvenli bir şekilde hashle
    
        // RSA anahtar çiftini oluştur
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048, SecureRandom.getInstanceStrong()); // Güvenli bir rastgelelik sağlayıcı kullan
        KeyPair keyPair = keyGen.generateKeyPair();
    
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded(); // İsteğe bağlı olarak özel anahtar da alınıyor
    
        user.setPublicKey(publicKey);
        user.setPrivateKey(privateKey); // Özel anahtarı kullanıcı modeline ekleyin
        user.setCreatedDate(sdf.format(new java.util.Date())); // Kayıt tarihini GG-AA-YYYY:hh:mm:ss şeklinde ayarla
    
        return userRepository.save(user);
    }
}
