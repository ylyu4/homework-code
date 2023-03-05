package tw.training.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.training.homework.model.Commodity;
import tw.training.homework.service.CommodityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shopping")
public class CommodityController {

    private final CommodityService commodityService;

    @GetMapping("/commodities")
    public List<Commodity> getAllCommodities() {
        return commodityService.getCommodityList();
    }

    @GetMapping("/commodities/{id}")
    public Commodity getCommodity(@PathVariable Long id) {
        return commodityService.getCommodityById(id);
    }

}
