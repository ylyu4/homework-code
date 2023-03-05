package tw.training.homework.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tw.training.homework.exception.CommodityDuplicateException;
import tw.training.homework.exception.CredentialsIncorrectException;
import tw.training.homework.model.Commodity;
import tw.training.homework.model.request.CommodityRequest;
import tw.training.homework.model.request.ImageRequest;
import tw.training.homework.model.request.PriceRequest;
import tw.training.homework.repository.CommodityRepository;

import java.util.Arrays;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private CommodityRepository commodityRepository;


    @Test
    void should_create_new_commodity_successfully() {

        // given
        CommodityRequest commodityRequest = new CommodityRequest("sku1", "title1",
                "this is the description",
                new PriceRequest( 20),
                Arrays.asList(new ImageRequest("www.xxx.com", "none"),
                        new ImageRequest("www.yyy.com", "none")));
        when(commodityRepository.findBySku("sku1")).thenReturn(Optional.empty());

        // when
        adminService.createNewCommodity(commodityRequest);

        // then
        verify(commodityRepository, times(1)).save(any());
    }

    @Test
    void should_throw_exception_when_sku_is_duplicated() {

        // given
        CommodityRequest commodityRequest = new CommodityRequest("sku1", "title1",
                "this is the description",
                new PriceRequest(20),
                Arrays.asList(new ImageRequest("www.xxx.com", "none"),
                        new ImageRequest("www.yyy.com", "none")));
        when(commodityRepository.findBySku("sku1")).thenReturn(Optional.of(new Commodity()));


        // then
        assertThrows(CommodityDuplicateException.class,
                () -> adminService.createNewCommodity(commodityRequest),
                "The sku: sku1 is duplicated.");
    }


}