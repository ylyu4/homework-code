package tw.training.homework.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tw.training.homework.exception.CommodityNotFoundException;
import tw.training.homework.model.Commodity;
import tw.training.homework.model.Image;
import tw.training.homework.model.Price;
import tw.training.homework.service.CommodityService;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
class ShoppingControllerTest {

    @MockBean
    private CommodityService commodityService;

    @Autowired
    private MockMvc mockMvc;

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
    void should_return_commodity_list_to_user_successfully() throws Exception {

        doReturn(Arrays.asList(commodity1, commodity2)).when(commodityService).getCommodityList();

        mockMvc.perform(get("/shopping/commodities")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.[0].sku", containsString("sku1")))
                        .andExpect(jsonPath("$.[1].sku", containsString("sku2")));
    }


    @Test
    void should_return_commodity_to_user_by_id() throws Exception {

        doReturn(commodity1).when(commodityService).getCommodityById(1L);

        mockMvc.perform(get("/shopping/commodities/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.sku", containsString("sku1")))
                        .andExpect(jsonPath("$.title", containsString("title1")));
    }

    @Test
    void should_receive_404_when_commodity_is_not_found() throws Exception {

        when(commodityService.getCommodityById(1L)).thenThrow(new CommodityNotFoundException("Commodity is not found by this id: 1"));

        mockMvc.perform(get("/shopping/commodities/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CommodityNotFoundException))
                .andExpect(result -> assertEquals("Commodity is not found by this id: 1", result.getResolvedException().getMessage()));
    }
}