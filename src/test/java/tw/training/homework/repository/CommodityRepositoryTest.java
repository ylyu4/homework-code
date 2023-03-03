package tw.training.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import tw.training.homework.model.Commodity;
import tw.training.homework.model.Image;
import tw.training.homework.model.Price;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestEntityManager
public class CommodityRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommodityRepository commodityRepository;


    private Commodity commodity1;

    private Commodity commodity2;

    @BeforeEach
    public void setup() {
        Price price = new Price(20);
        Image image1 = new Image("www.xxx.com", "none");
        Image image2 = new Image("www.yyy.com", "none");

        commodity1 = new Commodity("sku1", "title1",
                "this is the description",
                price, Arrays.asList(image1, image2));
        commodity2 = new Commodity("sku2", "title2",
                "this is the description",
                price, Arrays.asList(image1, image2));
    }


    @Test
    void should_find_commodity_by_sku_successfully() {
        // given
        entityManager.persistAndFlush(commodity1);

        // when
        Optional<Commodity> optionalCommodity = commodityRepository.findBySku("sku1");


        // then
        assertTrue(optionalCommodity.isPresent());
        assertEquals("title1", optionalCommodity.get().getTitle());
        assertEquals("this is the description", optionalCommodity.get().getDescription());
    }

    @Test
    void should_find_all_commodities_successfully() {
        // given
        entityManager.persistAndFlush(commodity1);
        entityManager.persistAndFlush(commodity2);

        // when
        List<Commodity> commodityList = commodityRepository.findAll();


        // then
        assertEquals(2, commodityList.size());
        assertEquals("sku1", commodityList.get(0).getSku());
        assertEquals("sku2", commodityList.get(1).getSku());
    }

    @Test
    void should_find_commodity_by_id_successfully() {
        // given
        entityManager.persistAndFlush(commodity1);

        // when
        Long id = commodityRepository.findAll().get(0).getId();
        Commodity commodity = commodityRepository.findById(id).get();


        // then
        assertEquals("sku1", commodity.getSku());
        assertEquals("title1", commodity.getTitle());
    }
}
