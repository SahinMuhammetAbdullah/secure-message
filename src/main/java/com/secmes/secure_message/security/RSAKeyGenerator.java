package com.secmes.secure_message.security;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class RSAKeyGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        try (FileOutputStream out = new FileOutputStream("src/main/resources/private_key.pem")) {
            out.write(pair.getPrivate().getEncoded());
        }
        try (FileOutputStream out = new FileOutputStream("src/main/resources/public_key.pem")) {
            out.write(pair.getPublic().getEncoded());
        }

        System.out.println("Keys Generated Successfully!");
    }
}
