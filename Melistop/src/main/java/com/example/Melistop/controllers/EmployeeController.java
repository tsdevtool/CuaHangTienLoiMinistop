package com.example.Melistop.controllers;

import com.example.Melistop.models.Order;
import com.example.Melistop.models.OrderDetail;
import com.example.Melistop.models.Payment;
import com.example.Melistop.models.Product;
import com.example.Melistop.models.User;
import com.example.Melistop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private OrderManagementService orderManagementService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentServiceOrder paymentService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String admin() {
        return "layoutemployee";
    }

    @GetMapping("/orders/list")
    public String listEmployeeOrders(Model model) {
        model.addAttribute("orders", orderManagementService.findDeliveredOrders());
        return "employee/orders/list";
    }

    @GetMapping("/orders/pending")
    public String listPendingOrders(Model model) {
        model.addAttribute("orders", orderManagementService.findPendingOrders());
        return "employee/orders/pending-list";
    }

    @PostMapping("/orders/status/{id}")
    public String updateOrderStatus(@PathVariable("id") Long id, @RequestParam("source") String source) {
        Optional<Order> orderOpt = orderManagementService.findOrderById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if ("list".equals(source)) {
                order.setStatus(false);
            } else if ("pending".equals(source)) {
                order.setStatus(true);
            }
            orderManagementService.updateOrder(id, order);
        }
        return "redirect:/employee/orders/" + source;
    }

    @GetMapping("/orders/detail/{id}")
    public String viewOrderDetails(@PathVariable("id") Long id, Model model) {
        Optional<Order> orderOpt = orderManagementService.findOrderById(id);
        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
        }
        return "employee/orders/detail";
    }

    @GetMapping("/orders/edit/{id}")
    public String showEditOrderForm(@PathVariable("id") Long id, Model model) {
        Optional<Order> orderOpt = orderManagementService.findOrderById(id);
        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
        }
        return "employee/orders/edit";
    }

    @PostMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable("id") Long id, @ModelAttribute("order") Order order) {
        orderManagementService.updateOrder(id, order);
        return "redirect:/employee/orders";
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderManagementService.deleteOrder(id);
        return "redirect:/employee/orders/pending";
    }

    @GetMapping("/orders/add")
    public String showAddOrderForm(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("payments", paymentService.findAllPayments());
        model.addAttribute("order", new Order());
        return "employee/orders/add-order";
    }

    @PostMapping("/orders/add")
    @ResponseBody
    public ResponseEntity<?> addOrder(@RequestParam("productIds") List<Long> productIds,
                                      @RequestParam("quantities") List<Integer> quantities,
                                      @RequestParam("prices") List<Double> prices,
                                      @RequestParam("paymentId") Long paymentId) {

        User user = userService.findById(1L).orElse(null); // Sửa lại logic để lấy user đang đăng nhập
        Payment payment = paymentService.findPaymentById(paymentId).orElse(null);

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setPayment(payment);
        order.setStatus(false); // Đơn hàng chờ xử lý

        List<OrderDetail> orderDetails = new ArrayList<>();
        double totalPrice = 0;

        for (int i = 0; i < productIds.size(); i++) {
            Optional<Product> productOpt = productService.getProductById(productIds.get(i));
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(product);
                orderDetail.setQuantity(quantities.get(i));
                orderDetail.setPrice(prices.get(i));
                orderDetails.add(orderDetail);
                totalPrice += quantities.get(i) * prices.get(i);
            }
        }

        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalPrice);

        orderManagementService.saveOrder(order);

        // Nếu phương thức thanh toán là tiền mặt, trả về đơn hàng
        if (payment != null && "Tiền mặt".equals(payment.getName())) {
            return ResponseEntity.ok(order);
        }

        // Nếu phương thức thanh toán không phải tiền mặt, trả về ID để tạo mã QR
        return ResponseEntity.ok(order.getId());
    }

    @GetMapping("/orders/qr-code/momo")
    public String showMomoQRCode(Model model) {
        model.addAttribute("qrCode", "/image/momo.jpg");
        return "employee/orders/qr-code";
    }

    @GetMapping("/orders/qr-code/nganhang")
    public String showNganHangQRCode(Model model) {
        model.addAttribute("qrCode", "/image/nganhang.jpg");
        return "employee/orders/qr-code";
    }

}
