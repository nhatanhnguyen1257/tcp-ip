package com.server.rsa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;

public class RSA {

    private static byte[] key_private;
    private static byte[] key_public;

    {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(RSA.class.getResource("/res/privateKey.rsa").getPath());
            RSA.key_private = new byte[fis.available()];
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fis != null) {
                try {
                    fis.read(RSA.key_private);
                } catch (IOException ex) {
                    Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        FileInputStream fisPrivate = null;
        try {
            fisPrivate = new FileInputStream(RSA.class.getResource("/res/publicKey.rsa").getPath());
            RSA.key_public = new byte[fisPrivate.available()];
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fisPrivate != null) {
                try {
                    fisPrivate.read(RSA.key_public);
                } catch (IOException ex) {
                    Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    fisPrivate.close();
                } catch (IOException ex) {
                    Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * method giải mã dữ liệu được mã hóa
     *
     * @param data : dữ liệu json object được mã hóa
     * @return dữ liệu json của object
     * @throws Exception
     */
    protected final static String decryption(String data) throws Exception {
        try {
            // Đọc file chứa private key
//            FileInputStream fis = new FileInputStream(RSA.class.getResource("/res/privateKey.rsa").getPath());
//            byte[] b = new byte[fis.available()];
//            fis.read(b);
//            fis.close();

            // Tạo private key
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(RSA.key_private);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = factory.generatePrivate(spec);

            // Giải mã dữ liệu
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKey);
            return new String(c.doFinal(Base64.getDecoder().decode(data)));
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * mã hóa dữ liệu.
     *
     * @param input object dưới dạng json
     * @return dữ liệu object dưới dạng json được mã hóa
     * @throws Exception
     */
    protected final static String encrpytion(String input) throws Exception {
        try {
            // Đọc file chứa public key
//            FileInputStream fis = new FileInputStream(RSA.class.getResource("/res/publicKey.rsa").getPath());
//            byte[] b = new byte[fis.available()];
//            fis.read(b);
//            fis.close();

            // Tạo public key
            X509EncodedKeySpec spec = new X509EncodedKeySpec(RSA.key_public);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);

            // Mã hoá dữ liệu
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.getEncoder().encodeToString(c.doFinal(input.getBytes()));
        } catch (Exception ex) {
            throw ex;
        }
    }
}
