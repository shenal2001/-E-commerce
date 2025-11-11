/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

/**
 *
 * @author prave
 */
public class User_DTO implements Serializable {

    @Expose   
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String email;
    @Expose(deserialize = true,serialize = false)
    private String password;
    
    @Expose
    private String verification_code;

    public  User_DTO() {
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the verification_code
     */
    public String getVerification_code() {
        return verification_code;
    }

    /**
     * @param verification_code the verification_code to set
     */
    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    /**
     * @return the address_id
     */




}

