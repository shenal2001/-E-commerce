/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author prave
 */
public class Validation {
        public static boolean isEmailValid(String email){
        return email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$");
    }
    
    public static boolean isPasswordValid(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
    
    public static boolean isVerificationCodeValid(String verificationCode){
        return verificationCode.matches("^\\d+$");
    }
    
    public static boolean isInteger(String value){
        return value.matches("^\\d+$");
    }
    
     public static boolean isDouble(String value){
        return value.matches("^^\\d+(\\.\\d{2})?$");
    }

}

