package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Brand;
import entity.Category;
import entity.Color;
import entity.Product;
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
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "Search", urlPatterns = {"/search"})
public class SearchProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("work");
        Gson gson = new Gson();

        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("success", false);

        //get request json
        JsonObject requestJsonObject = gson.fromJson(request.getReader(), JsonObject.class);

        Session session = HibernateUtil.getSessionFactory().openSession();

        //search all products
        Criteria criteria1 = session.createCriteria(Product.class);

        //add category filter
        if (requestJsonObject.has("category_name")) {
            //color selected
            String category = requestJsonObject.get("category_name").getAsString();

            //get condition from Db
            Criteria criteria5 = session.createCriteria(Category.class);
            if (!category.equals("Select Category")) {
                criteria5.add(Restrictions.eq("id", Integer.parseInt(category)));
                Category productCategory = (Category) criteria5.uniqueResult();

                //filter products by condition from DB
                criteria1.add(Restrictions.eq("category", productCategory));
            }

        }

        if (requestJsonObject.has("brand_name")) {
            //condition selected
            String brand = requestJsonObject.get("brand_name").getAsString();

            //get condition from Db
            Criteria criteria4 = session.createCriteria(Brand.class);
            if (!brand.equals("Select Brand")) {
                criteria4.add(Restrictions.eq("id", Integer.parseInt(brand)));
                Brand product_brand = (Brand) criteria4.uniqueResult();

                //filter products by condition from DB
                criteria1.add(Restrictions.eq("brand", product_brand));
            }

        }
//
//        if (requestJsonObject.has("color")) {
//            //color selected
//            String color = requestJsonObject.get("color").getAsString();
//
//            //get condition from Db
//            Criteria criteria5 = session.createCriteria(Color.class);
//            criteria5.add(Restrictions.eq("name", color));
//            Color productColor = (Color) criteria5.uniqueResult();
//
//            //filter products by condition from DB
//            criteria1.add(Restrictions.eq("color", productColor));
//
//        }

        if (requestJsonObject.has("sub_category")) {
            //color selected
            String sub_category = requestJsonObject.get("sub_category").getAsString();

            //get condition from Db
            Criteria criteria6 = session.createCriteria(Sub_Category.class);
            if (!sub_category.equals("Select Type")) {
                criteria6.add(Restrictions.eq("id", Integer.parseInt(sub_category)));
                Sub_Category product_sub_category = (Sub_Category) criteria6.uniqueResult();

                //filter products by condition from DB
                criteria1.add(Restrictions.eq("subCategory", product_sub_category));
            }

        }

        if (requestJsonObject.has("size")) {
            //color selected
            String size = requestJsonObject.get("size").getAsString();

            //get condition from Db
            Criteria criteria6 = session.createCriteria(Size.class);
            if (!size.equals("Select Size")) {
                criteria6.add(Restrictions.eq("id", Integer.parseInt(size)));
                Size productSize = (Size) criteria6.uniqueResult();

                //filter products by condition from DB
                criteria1.add(Restrictions.eq("size", productSize));
            }

        }

//        double startPrice = requestJsonObject.get("price_range_start").getAsDouble();
//        double endPrice = requestJsonObject.get("price_range_end").getAsDouble();
//
//        criteria1.add(Restrictions.ge("price", startPrice));
//        criteria1.add(Restrictions.le("price", endPrice));
        //filter products by sort from Db
//        String sortText = requestJsonObject.get("sort_text").getAsString();
//
//        if (sortText.equals("Sort by Latest")) {
//            criteria1.addOrder(Order.desc("id"));
//
//        } else if (sortText.equals("Sort by Oldest")) {
//            criteria1.addOrder(Order.asc("id"));
//
//        } else if (sortText.equals("Sort by Name")) {
//            criteria1.addOrder(Order.asc("title"));
//
//        } else if (sortText.equals("Sort by price")) {
//            criteria1.addOrder(Order.asc("price"));
//
//        }
        //get all product count
        responseJsonObject.addProperty("allProductCount", criteria1.list().size());

        //set product range
//        int firstResult = requestJsonObject.get("firstResult").getAsInt();
//        criteria1.setFirstResult(firstResult);
//        criteria1.setMaxResults(4);
        //get product list
        List<Product> productList = criteria1.list();
        System.out.println(productList.size());

        //get product list
        for (Product product : productList) {
            product.setUser(null);
        }

        responseJsonObject.addProperty("success", true);
        responseJsonObject.add("productList", gson.toJsonTree(productList));

        //send response
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseJsonObject));

    }

}
