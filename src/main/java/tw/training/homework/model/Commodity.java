package tw.training.homework.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Entity
@Table(name = "commodity")
public class Commodity {

    @Id
    private String sku;

    private String title;

    private String description;

    @OneToOne
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;

    @OneToMany
    @JoinColumn(name = "url", nullable = false)
    @Column(name = "image_urls")
    private List<Image> imagesUrl;

}
