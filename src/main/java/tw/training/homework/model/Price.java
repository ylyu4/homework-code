package tw.training.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.training.homework.model.request.PriceRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Integer amount;

    @JsonIgnore
    @OneToOne(mappedBy = "price")
    private Commodity commodity;

    public Price(Integer amount) {
        this.amount = amount;
    }

    public Price(PriceRequest priceRequest) {
        this.amount = priceRequest.getAmount();
    }

}
