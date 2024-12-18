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
<<<<<<< Updated upstream
import java.util.Base64;
=======
import java.text.SimpleDateFormat;
>>>>>>> Stashed changes

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password) throws NoSuchAlgorithmException {
        User user = new User();
        user.setUsername(username);
<<<<<<< Updated upstream
        user.setPassword(passwordEncoder.encode(password)); // Şifreyi güvenli bir şekilde hashle

        // RSA anahtar çiftini oluştur
=======
        user.setPassword(passwordEncoder.encode(password));
    
>>>>>>> Stashed changes
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048, SecureRandom.getInstanceStrong());
        KeyPair keyPair = keyGen.generateKeyPair();

        byte[] publicKey = keyPair.getPublic().getEncoded();
<<<<<<< Updated upstream
        byte[] privateKey = keyPair.getPrivate().getEncoded(); // İsteğe bağlı olarak özel anahtar da alınıyor

        user.setPublicKey(publicKey);
        user.setPrivateKey(privateKey); // Özel anahtarı kullanıcı modeline ekleyin
        user.setCreatedDate(new java.util.Date()); // Kayıt tarihini ayarla

=======
        byte[] privateKey = keyPair.getPrivate().getEncoded();
    
        user.setPublicKey(publicKey);
        user.setPrivateKey(privateKey);
        user.setCreatedDate(sdf.format(new java.util.Date()));
    
>>>>>>> Stashed changes
        return userRepository.save(user);
    }
}
