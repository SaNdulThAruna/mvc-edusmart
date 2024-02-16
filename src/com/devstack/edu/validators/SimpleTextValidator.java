package com.devstack.edu.validators;

public class SimpleTextValidator {
    public static boolean validateName(String name){
        return name.matches("^[a-zA-Z]{3,45}$");
    }
    public static boolean validateDob(String dob){
        return dob.matches("\\d{4}-\\d{2}-\\d{2}");
    }
    public static boolean validateEmail(String email){
        return email.matches("");
    }
    public static boolean validateAddress(String address){
        return address.matches("\\w+\\s+\\w+");
    }
}
