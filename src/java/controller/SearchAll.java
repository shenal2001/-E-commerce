/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Product;
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
import org.hibernate.criterion.Order;

/**
 *
 * @author prave
 */
@WebServlet(name = "SelectAll", value = "/selectAll")
public class SearchAll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        Session session = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(Product.class);

        criteria.addOrder(Order.asc("date_time_added"));

        Product product = (Product) criteria.uniqueResult();
        product.getUser().setEmail(null);
        product.getUser().setPassword(null);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("product", gson.toJsonTree(product));

        session.close();
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonObject));

    }

}
