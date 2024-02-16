package com.devstack.edu.controller;

import sun.plugin2.ipc.windows.WindowsNamedPipe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static void main(String[] args) {

        //basic char (^,$)
        //Char Class []

        String regex = "^[a-z0-9]@gmail.com"; //

        Pattern pattern = Pattern.compile(regex);
        String input = "sandul1@gmail.com";
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()){
            System.out.println(true);
        }else{
            System.out.println(false);
        }
    }
}
