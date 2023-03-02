package tw.training.homework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tw.training.homework.model.Commodity;
import tw.training.homework.model.Image;
import tw.training.homework.model.Price;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@AutoConfigureTestEntityManager
public class CommodityRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommodityRepository commodityRepository;

    @Test
    void should_find_commodity_by_sku_successfully() {
        // given
        Price price = new Price(20);

        Image image1 = new Image("www.xxx.com", "none");
        Image image2 = new Image("www.yyy.com", "none");

        Commodity commodity = new Commodity("sku1", "title1",
                "this is the description",
                price, Arrays.asList(image1, image2));

        entityManager.persistAndFlush(commodity);

        // when
        Optional<Commodity> optionalCommodity = commodityRepository.findBySku("sku1");


        // then
        assertTrue(optionalCommodity.isPresent());
        assertEquals("title1", optionalCommodity.get().getTitle());
        assertEquals("this is the description", optionalCommodity.get().getDescription());
    }
}
