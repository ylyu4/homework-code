package tw.training.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tw.training.homework.exception.CommodityDuplicateException;
import tw.training.homework.model.request.CommodityRequest;
import tw.training.homework.model.request.ImageRequest;
import tw.training.homework.model.request.PriceRequest;
import tw.training.homework.service.AdminService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
public class AdminControllerTest {

    @MockBean
    private AdminService adminService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CommodityRequest> request;


    @Test
    void should_create_new_commodity_successfully() throws Exception {
        CommodityRequest commodityRequest = new CommodityRequest("sku1", "title1",
                "this is the description",
                new PriceRequest(20),
                Arrays.asList(new ImageRequest("www.xxx.com", "none"),
                        new ImageRequest("www.yyy.com", "none")));

        doNothing().when(adminService).createNewCommodity(any());

        mockMvc.perform(post("/admin/commodities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(commodityRequest).getJson()))
                .andExpect(status().isCreated());
    }

    @Test
    void should_throw_exception_when_username_is_taken() throws Exception {
        CommodityRequest commodityRequest = new CommodityRequest("sku1", "title1",
                "this is the description",
                new PriceRequest(20),
                Arrays.asList(new ImageRequest("www.xxx.com", "none"),
                        new ImageRequest("www.yyy.com", "none")));
        doThrow(new CommodityDuplicateException("The sku: sku1 is duplicated.")).when(adminService).createNewCommodity(any());

        mockMvc.perform(post("/admin/commodities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(commodityRequest).getJson()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CommodityDuplicateException))
                .andExpect(result -> assertEquals("The sku: sku1 is duplicated.", result.getResolvedException().getMessage()));
    }

}
