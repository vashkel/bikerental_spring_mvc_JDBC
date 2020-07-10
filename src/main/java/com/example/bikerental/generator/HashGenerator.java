package com.example.bikerental.generator;

import javafx.util.Pair;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class HashGenerator {

    private static final int LENGTH = 20;

    public Pair<String, String> generateHashSalt(String password) throws NoSuchAlgorithmException {
        String saltString = nextSalt();
        String hash = generateHash(password, saltString);
        return new Pair<>(hash, saltString);
    }

    public String generateHash(String password, String salt) throws NoSuchAlgorithmException {
        String algorithm = "MD5";
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(salt.getBytes());
        byte[] hash = messageDigest.digest(password.getBytes());
        return DatatypeConverter.printHexBinary(hash).toLowerCase();
    }

    private String nextSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[LENGTH];
        random.nextBytes(bytes);
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }

}

