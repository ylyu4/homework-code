package tw.training.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.training.homework.model.Commodity;
import tw.training.homework.repository.CommodityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommodityService {

    private final CommodityRepository commodityRepository;


    public List<Commodity> getCommodityList() {
        return commodityRepository.findAll();
    }

}
