/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Response_DTO;
import dto.User_DTO;
import java.io.IOException;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author prave
 */
@WebServlet(name = "SignIn", value = "/signin")
public class SignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        User_DTO user_dto = gson.fromJson(req.getReader(), User_DTO.class);

        Response_DTO response = new Response_DTO();

        if (user_dto.getEmail().isEmpty()) {
            response.setContent("Please Enter Your Email");
        } else if (user_dto.getPassword().isEmpty()) {
            response.setContent("Please Enter Your Password");
        } else {
//try to add another thread to here
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", user_dto.getEmail()));
            criteria1.add(Restrictions.eq("password", user_dto.getPassword()));

            if (!criteria1.list().isEmpty()) {

                User user = (User) criteria1.list().get(0);
                response.setContent("Login Success");

                if (user.getVerified().equals("1")) {

                    user_dto.setFirstName(user.getFirst_name());
                    user_dto.setLastName(user.getLast_name());
                    user_dto.setEmail(user.getEmail());

                    req.getSession().setAttribute("user", user_dto);

                    response.setContent("Login Success");

                } else {
                    req.getSession().setAttribute("email", user_dto.getEmail());
                    response.setContent("Not a Verified User");
                }

            } else {
                response.setContent("Invalid Login Details");
            }

        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response));

    }

}
