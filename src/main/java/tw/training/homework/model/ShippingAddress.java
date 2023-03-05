package tw.training.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tw.training.homework.model.request.ShippingAddressRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "shipping_address")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String address;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "shippingAddress")
    @JsonIgnore
    private Order order;

    public ShippingAddress(ShippingAddressRequest request) {
        this.address = request.getAddress();
        this.fullName = request.getFullName();
        this.phoneNumber = request.getPhoneNumber();
    }
}
