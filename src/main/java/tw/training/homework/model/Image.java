package tw.training.homework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.training.homework.model.request.ImageRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String url;

    @Column(name = "alternate_text")
    private String alternateText;

    public Image(String url, String alternateText) {
        this.url = url;
        this.alternateText = alternateText;
    }

    public Image(ImageRequest request) {
        this.url = request.getUrl();
        this.alternateText = request.getAlternateText();
    }

}
