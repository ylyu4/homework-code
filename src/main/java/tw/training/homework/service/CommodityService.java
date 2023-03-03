package tw.training.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.training.homework.exception.CommodityNotFoundException;
import tw.training.homework.model.Commodity;
import tw.training.homework.repository.CommodityRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommodityService {

    private final CommodityRepository commodityRepository;

    public List<Commodity> getCommodityList() {
        return commodityRepository.findAll();
    }

    public Commodity getCommodityById(Long id) {
        Optional<Commodity> optionalCommodity = commodityRepository.findById(id);
        if (!optionalCommodity.isPresent()) {
            throw new CommodityNotFoundException(String.format("Commodity is not found by this id: ", id));
        }
        return optionalCommodity.get();
    }

}
