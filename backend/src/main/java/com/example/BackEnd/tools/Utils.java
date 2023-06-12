package com.example.BackEnd.tools;

import com.example.BackEnd.models.Country;
import org.springframework.security.crypto.codec.Hex;
import java.security.MessageDigest;
import java.util.Optional;
import com.example.BackEnd.models.Artist;
import com.example.BackEnd.models.Museum;
import com.example.BackEnd.controllers.ArtistController;
import com.example.BackEnd.controllers.MuseumController;
import com.example.BackEnd.repositories.MuseumRepository;
import com.example.BackEnd.repositories.ArtistRepository;

public class Utils {
    public static String ComputeHash(String pwd, String salt)
    {
        MessageDigest digest;
        byte[] w = Hex.decode(new String(Hex.encode(pwd.getBytes())) + salt);
        try {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (Exception ex) {
            return new String();
        }
        return new String(Hex.encode(digest.digest(w)));
    }

}