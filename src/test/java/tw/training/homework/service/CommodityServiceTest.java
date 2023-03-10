package tw.training.homework.service;

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
import tw.training.homework.service.domain.CommodityDomainService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CommodityServiceTest {


    @InjectMocks
    private CommodityService commodityService;

    @Mock
    private CommodityDomainService commodityDomainService;


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
    void should_find_all_commodities() {
        // given
        when(commodityDomainService.getAllCommodity()).thenReturn(Arrays.asList(commodity1, commodity2));

        // when
        List<Commodity> commodityList = commodityService.getCommodityList();

        // then
        assertEquals(2, commodityList.size());
        assertEquals("sku1", commodityList.get(0).getSku());
        assertEquals("sku2", commodityList.get(1).getSku());
    }


    @Test
    void should_find_commodity_by_id_successfully() {
        // given
        when(commodityDomainService.getCommodityById(1L)).thenReturn(commodity1);

        // when
        Commodity res = commodityService.getCommodityById(1L);

        // then
        assertEquals("sku1", res.getSku());
        assertEquals("title1", res.getTitle());
    }

    @Test
    void should_throw_exception_when_commodity_does_not_exist() {
        // given
        when(commodityDomainService.getCommodityById(1L)).thenThrow(CommodityNotFoundException.class);

        // then
        assertThrows(CommodityNotFoundException.class, () -> commodityService.getCommodityById(1L), "Commodity is not found by this id: 1");
    }

}