package com.devstack.edu.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordManager {

    public static String encrypt(String text){
        return BCrypt.hashpw(text,BCrypt.gensalt());
    }

    public static boolean checkPw(String text,String hash){
        return BCrypt.checkpw(text,hash);
    }

}
