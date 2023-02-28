package tw.training.homework.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "Price")
public class Price {

    @Id
    @Column(name = "price_id")
    private Long priceId;

    private Integer amount;

}
