package tw.training.homework.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "image")
public class Image {

    @Id
    private String url;

    @Column(name = "alternate_text")
    private String alternateText;

}
