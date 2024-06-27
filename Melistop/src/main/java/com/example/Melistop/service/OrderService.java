package com.example.Melistop.service;

import com.example.Melistop.models.Customer;
import com.example.Melistop.models.Order;
import com.example.Melistop.models.OrderDetail;
import com.example.Melistop.models.Product;
import com.example.Melistop.repository.CustomerRepository;
import com.example.Melistop.repository.OrderRepository;
import com.example.Melistop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    //Xem chi tiet san pham trong gio hang cua user do
    public List<OrderDetail> getOrderDertailByCustomerId(Long customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Order order = customer.getOrders().stream()
                .filter(o -> o.getStatus().equals("PENDING"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No pending order found"));
        return order.getOrderDetails();
    }

//    //Xem thong tin san pham duoc lay trong gio hang
//    public void test(){
//        List<OrderDetail> test = getOrderDertailByCustomerId(1);
//    }
    //Them san pham vao gio hang
    public void addProductToOrder(Long customerId, Long productId, Long quantity){
        //Kiem tra xem Customer da co thong tin gio hang nao chua neu chua thi them vao
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty()){
            throw new RuntimeException("Customer not found");
        }

        Customer customer = customerOptional.get();

        //Lay don hang dau tien voi don hang co trang thai PENDING, neu khong se tao mot doi tuong Order moi
        Order order = customer.getOrders().stream()
                .filter(o -> o.getStatus().equals("PENDING"))
                .findFirst()
                .orElse(new Order());

        //Set cac gia tri customer va status = peding
        if(order.getId() == null){
            order.setCustomer(customer);
            order.setStatus("PENDING");
            orderRepository.save(order);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setOrder(order);

        //Them thong tin san pham vao gio hang
        order.getOrderDetails().add(orderDetail);
        orderRepository.save(order);
    }
}
