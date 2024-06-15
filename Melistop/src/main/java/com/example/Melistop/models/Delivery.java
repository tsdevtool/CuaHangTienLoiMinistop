package com.example.Melistop.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "deliver_id")
    private Long deliverId;

    @Column(name = "status")
    private Integer status;

    // getters and setters
}
