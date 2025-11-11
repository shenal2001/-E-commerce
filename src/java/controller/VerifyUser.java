/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.Response_DTO;
import dto.User_DTO;
import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author prave
 */
@WebServlet(name = "VerifyUser", value = "/verifyUser")
public class VerifyUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        User_DTO user_dto = gson.fromJson(req.getReader(), User_DTO.class);

        Response_DTO response_dto = new Response_DTO();
        
        if (user_dto.getVerification_code().isEmpty()) {
            response_dto.setContent("Please Enter Your Verification Code");
        } else if (!Validation.isVerificationCodeValid(user_dto.getVerification_code())) {
            response_dto.setContent("VErification Code can only contains numbers");
        } else if (user_dto.getEmail().isEmpty()) {
            response_dto.setContent("Please Enter the registered email");
        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria1 = session.createCriteria(User.class);

            criteria1.add(Restrictions.eq("email", user_dto.getEmail()));
            criteria1.add(Restrictions.eq("verification_code", user_dto.getVerification_code()));

            if (!criteria1.list().isEmpty()) {
                User user = (User) criteria1.list().get(0);
                user.setVerified("1");
                session.update(user);
                session.beginTransaction().commit();
                response_dto.setContent("Success");
            } else {
                response_dto.setContent("Invalid Details Please Try Again");
            }
        }
        
         resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response_dto));

    }

}
