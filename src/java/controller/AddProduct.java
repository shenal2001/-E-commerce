package controller;

import com.google.gson.Gson;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Brand;
import entity.Category;
import entity.Color;
import entity.Product;
import entity.Size;
import entity.Sub_Category;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.HibernateUtil;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@MultipartConfig
@WebServlet(name = "addPorduct", urlPatterns = {"/addProduct"})
public class AddProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Response_DTO response_DTO = new Response_DTO();

        Gson gson = new Gson();

        String productName = request.getParameter("productName");
        String brandSelect = request.getParameter("brandSelect");
        String categorySelect = request.getParameter("categorySelect");
        String colorSelect = request.getParameter("colorSelect");
        String subCategorySelect = request.getParameter("subCategorySelect");
        String sizeSelect = request.getParameter("sizeSelect");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");
        String description = request.getParameter("description");

        Part image1 = request.getPart("image1");
        Part image2 = request.getPart("image2");
        Part image3 = request.getPart("image3");

        Session session = HibernateUtil.getSessionFactory().openSession();

        if (productName.isEmpty()) {
            response_DTO.setContent("Enter the Product Name");

        } else if (!Validation.isInteger(brandSelect)) {
            response_DTO.setContent("Invalid Brand");

        } else if (!Validation.isInteger(categorySelect)) {
            response_DTO.setContent("Invalid Category");

        } else if (!Validation.isInteger(colorSelect)) {
            response_DTO.setContent("Invalid Color");

        } else if (!Validation.isInteger(subCategorySelect)) {
            response_DTO.setContent("Invalid Sub Category");

        } else if (!Validation.isInteger(sizeSelect)) {
            response_DTO.setContent("Invalid Size");

        } else if (price.isEmpty()) {
            response_DTO.setContent("Please fill Price");

        } else if (!Validation.isDouble(price)) {
            response_DTO.setContent("Invalid price");

        } else if (Double.parseDouble(price) <= 0) {
            response_DTO.setContent("Price must be greater than 0");

        } else if (quantity.isEmpty()) {
            response_DTO.setContent("Invalid Quantity");

        } else if (!Validation.isInteger(quantity)) {
            response_DTO.setContent("Invalid Quantity");

        } else if (Integer.parseInt(quantity) <= 0) {
            response_DTO.setContent("Quantity must be greater than 0");

        } else if (description.isEmpty()) {
            response_DTO.setContent("Enter the description");

        } else if (image1.getSubmittedFileName() == null) {
            response_DTO.setContent("Please upload image1");

        } else if (image2.getSubmittedFileName() == null) {
            response_DTO.setContent("Please upload image2");

        } else if (image3.getSubmittedFileName() == null) {
            response_DTO.setContent("Please upload image3");

        } else if (description.isEmpty()) {
            response_DTO.setContent("Enter the Product description");

        } else {
            Category category = (Category) session.get(Category.class, Integer.parseInt(categorySelect));

            if (category == null) {
                response_DTO.setContent("Please select a valid Category");

            } else {

                Brand brand = (Brand) session.get(Brand.class, Integer.parseInt(brandSelect));

                if (brand == null) {
                    response_DTO.setContent("Please select a valid Brand");

                } else {

                    Sub_Category sub_category = (Sub_Category) session.get(Sub_Category.class, Integer.parseInt(subCategorySelect));

                    if (sub_category == null) {
                        response_DTO.setContent("Please select a valid Sub Category");

                    } else {

                        Color color = (Color) session.get(Color.class, Integer.parseInt(colorSelect));

                        if (color == null) {
                            response_DTO.setContent("Please select a valid Color");

                        } else {

                            Size size = (Size) session.get(Size.class, Integer.parseInt(sizeSelect));

                            if (size == null) {
                                response_DTO.setContent("Please select a valid Product Size");

                            } else {

                                //to do Insert
                                Product product = new Product();

                                product.setColor(color);
                                product.setDate_time(new Date());
                                product.setDescription(description);
                                product.setBrand(brand);
                                product.setCategory(category);
                                product.setPrice(Double.parseDouble(price));
                                product.setQty(Integer.parseInt(quantity));
                                product.setSubCategory(sub_category);
                                product.setName(productName);
                                product.setSize(size);

                                //get user
                                User_DTO user_DTO = (User_DTO) request.getSession().getAttribute("user");

                                Criteria criteria = session.createCriteria(User.class);
                               
                                
                                criteria.add(Restrictions.eq("email", user_DTO.getEmail()));
                                User user = (User) criteria.uniqueResult();
                                product.setUser(user);

                                int pid = (int) session.save(product);
                                session.beginTransaction().commit();

                                String applicationPath = request.getServletContext().getRealPath("");
                                String newApplicationPath = applicationPath.replace("build" + File.separator + "web", "web");

                                File folder = new File(newApplicationPath + File.separator + "product-images" + File.separator + pid);
                                folder.mkdir();

                                File file1 = new File(folder, "image1.png");
                                InputStream inputStream = image1.getInputStream();
                                Files.copy(inputStream, file1.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                File file2 = new File(folder, "image2.png");
                                InputStream inputStream2 = image2.getInputStream();
                                Files.copy(inputStream2, file2.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                File file3 = new File(folder, "image3.png");
                                InputStream inputStream3 = image3.getInputStream();
                                Files.copy(inputStream3, file3.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                response_DTO.setSuccess(true);
                                response_DTO.setContent("Success");

                            }

                        }

                    }

                }

            }

        }

        session.close();

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(response_DTO));
    }

}
