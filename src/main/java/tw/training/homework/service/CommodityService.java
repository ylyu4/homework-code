package tw.training.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.training.homework.model.Commodity;
import tw.training.homework.service.domain.CommodityDomainService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommodityService {

    private final CommodityDomainService commodityDomainService;

    public List<Commodity> getCommodityList() {
        return commodityDomainService.getAllCommodity();
    }

    public Commodity getCommodityById(Long id) {
        return commodityDomainService.getCommodityById(id);
    }

}
