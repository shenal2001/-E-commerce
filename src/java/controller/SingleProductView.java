package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Product;
import java.io.IOException;
import java.util.List;
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

@WebServlet(name = "SingleProductView", urlPatterns = {"/singleProduct"})
public class SingleProductView extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            String productId = request.getParameter("pid");

            if (Validation.isInteger(productId)) {

                Product product = (Product) session.get(Product.class, Integer.parseInt(productId));
                product.getUser().setEmail(null);
                product.getUser().setPassword(null);

                JsonObject jsonObject = new JsonObject();
                jsonObject.add("product", gson.toJsonTree(product));

                session.close();
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(jsonObject));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
