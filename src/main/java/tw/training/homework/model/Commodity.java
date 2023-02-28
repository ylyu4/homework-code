package tw.training.homework.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commodity")
public class Commodity {

    @Id
    private String sku;

    private String title;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sku")
    private List<Image> imageUrls;

}
