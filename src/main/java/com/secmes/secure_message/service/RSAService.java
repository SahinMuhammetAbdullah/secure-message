package com.secmes.secure_message.service;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSAService {
    public byte[] encryptMessage(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }
}