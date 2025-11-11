/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import entity.Brand;
import entity.Category;
import entity.Color;
import entity.Size;
import entity.Sub_Category;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author prave
 */
@WebServlet(name = "loadAddProductFeatures", value = "/loadProductfeatures")
public class LoadAddProductFeatures extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();

        Session session = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria1 = session.createCriteria(Category.class);
        criteria1.addOrder(Order.asc("name"));
        List<Category> categoryList = criteria1.list();

        Criteria criteria2 = session.createCriteria(Brand.class);
        criteria2.addOrder(Order.asc("name"));
        List<Brand> brandList = criteria2.list();

        Criteria criteria3 = session.createCriteria(Color.class);
        criteria3.addOrder(Order.asc("name"));
        List<Color> colorList = criteria3.list();

        Criteria criteria4 = session.createCriteria(Sub_Category.class);
        criteria4.addOrder(Order.asc("id"));
        List<Sub_Category> sub_categoryList = criteria4.list();

        Criteria criteria5 = session.createCriteria(Size.class);
        criteria5.addOrder(Order.asc("name"));
        List<Size> sizeList = criteria5.list();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("categories", gson.toJsonTree(categoryList));
        jsonObject.add("brands", gson.toJsonTree(brandList));
        jsonObject.add("colors", gson.toJsonTree(colorList));
        jsonObject.add("sub_categories", gson.toJsonTree(sub_categoryList));
        jsonObject.add("sizes", gson.toJsonTree(sizeList));

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(jsonObject));

        
        session.close();

    }

}
