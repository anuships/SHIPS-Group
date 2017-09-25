package com.example.ships.myapplication.homepageAndRegistration;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public  class PasswordEncrypter {


    public static byte[] encryptPassword(String password, byte[] salt)
    throws NoSuchAlgorithmException, InvalidKeySpecException{
        String algorithm = "PBKDF2WithHmacSHA1";
        int derKeyLength = 160;
        int iters = 5000;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iters, derKeyLength);
        SecretKeyFactory kf = SecretKeyFactory.getInstance(algorithm);
        return kf.generateSecret(spec).getEncoded();
    }

    public static byte[] generateSalt() throws NoSuchAlgorithmException{
        SecureRandom ran = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        ran.nextBytes(salt);
        return salt;
    }
}
