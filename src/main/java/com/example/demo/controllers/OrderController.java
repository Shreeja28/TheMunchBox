package com.example.demo.controllers;

import com.example.demo.entities.Orders;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.services.OrderServices;
import com.example.demo.services.ProductServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderServices orderServices;
    @Autowired private ProductServices productServices;

    // Buy Product Page
    @GetMapping("/buy/{productId}")
    public String buyProduct(@PathVariable int productId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Product product = productServices.getProduct(productId);
        model.addAttribute("product", product);
        model.addAttribute("orders", orderServices.getOrdersForUser(user));
        model.addAttribute("name", user.getUname());
        return "BuyProduct";
    }

    // Place Order
    @PostMapping("/place")
    public String placeOrder(@RequestParam String oName, @RequestParam double oPrice,
                             @RequestParam int oQuantity, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Orders order = new Orders();
        order.setoName(oName);
        order.setoPrice(oPrice);
        order.setoQuantity(oQuantity);
        order.setOrderDate(new Date());
        order.setTotalAmmout(oPrice * oQuantity);
        order.setUser(user);

        orderServices.saveOrder(order);

        model.addAttribute("amount", order.getTotalAmmout());
        return "Order_success";
    }

    // Back to Products Page
    @GetMapping("/back")
    public String backToProducts() {
        return "redirect:/products";
    }
}
