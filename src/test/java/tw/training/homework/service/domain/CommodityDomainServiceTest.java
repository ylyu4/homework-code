package tw.training.homework.service.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tw.training.homework.exception.CommodityNotFoundException;
import tw.training.homework.model.Commodity;
import tw.training.homework.model.Image;
import tw.training.homework.model.Price;
import tw.training.homework.repository.CommodityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommodityDomainServiceTest {

    @InjectMocks
    CommodityDomainService commodityDomainService;

    @Mock
    CommodityRepository commodityRepository;

    private static Commodity commodity1;

    private static Commodity commodity2;

    @BeforeAll
    public static void setup() {
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
    void should_get_existed_commodity_successfully() {
        // given
        when(commodityRepository.findById(1L)).thenReturn(Optional.of(commodity1));

        // when
        Commodity commodity = commodityDomainService.getCommodityById(1L);

        // then
        assertEquals("sku1", commodity.getSku());
    }

    @Test
    void should_get_all_commodities_successfully() {
        // given
        when(commodityRepository.findAll()).thenReturn(Arrays.asList(commodity1, commodity2));

        // when
        List<Commodity> commodities = commodityDomainService.getAllCommodity();

        // then
        assertEquals(2, commodities.size());
    }

    @Test
    void should_throw_exception_when_commodity_is_not_found() {
        // given
        when(commodityRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThrows(CommodityNotFoundException.class, () -> commodityDomainService.getCommodityById(1L), "Commodity is not found by this id: 1");
    }


}