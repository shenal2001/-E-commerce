/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Response_DTO;
import dto.User_DTO;
import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import model.Mail;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author prave
 */
@WebServlet(name = "SignUp", value = "/signUp")
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        User_DTO user_dto = gson.fromJson(req.getReader(), User_DTO.class);

        Response_DTO response = new Response_DTO();
        if (user_dto.getFirstName().isEmpty()) {
            response.setContent("Please Enter Your First Name");
        } else if (user_dto.getLastName().isEmpty()) {
            response.setContent("Please Enter Your Last name");
        } else if (user_dto.getEmail().isEmpty()) {
            response.setContent("Please Enter Your Email");
        } else if (!Validation.isEmailValid(user_dto.getEmail())) {
//         email validation 
            response.setContent("Invalid Email Format. You can have only Letters, Numbers and _ , with your Email");
        } else if (user_dto.getPassword().isEmpty()) {
            response.setContent("Please Enter Your Password");
        } else if (!Validation.isPasswordValid(user_dto.getPassword())) {
//        password validation
            response.setContent("Invalid Password. (At least 8 characters, At least one uppercase letter, At least one lowercase letter, At least one digit, At least one special character (e.g., @, #, $, etc.))");
        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", user_dto.getEmail()));

            if (!criteria1.list().isEmpty()) {

                response.setContent("This Email Already Exists. Please try with another email");

            } else {

                int verificationCode = (int) (Math.random() * 100000);

                User user = new User();
                user.setEmail(user_dto.getEmail());
                user.setLast_name(user_dto.getLastName());
                user.setFirst_name(user_dto.getFirstName());
                user.setPassword(user_dto.getPassword());
                user.setVerification_code(String.valueOf(verificationCode));
                user.setStatus("0");
                
                Mail.sendMail(user_dto.getEmail(), "Sandy Fashion House, User Verification Code",String.valueOf(verificationCode));

                session.save(user);
                session.beginTransaction().commit();
                response.setContent("Registration Success. Please Check Your email to verify your account");

                session.close();

            }

        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response));

    }

}
