package tw.training.homework.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Integer quantity;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "order_status")
    private String orderStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private ShippingAddress shippingAddress;

    @JsonIgnore
    @ManyToOne
    private Commodity commodity;

    @JsonIgnore
    @ManyToOne
    private Customer customer;


    public Order(Integer quantity, Integer totalPrice, OrderStatus orderStatus) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus.name();
    }
    public Order(Integer quantity, Integer totalPrice, OrderStatus orderStatus,
                 Commodity commodity, Customer customer) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus.name();
        this.commodity = commodity;
        this.customer = customer;
    }
    public void updateStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus.name();
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
