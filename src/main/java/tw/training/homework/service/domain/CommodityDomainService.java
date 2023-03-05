package tw.training.homework.service.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.training.homework.exception.CommodityNotFoundException;
import tw.training.homework.model.Commodity;
import tw.training.homework.repository.CommodityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommodityDomainService {

    private final CommodityRepository commodityRepository;


    public List<Commodity> getAllCommodity() {
        return commodityRepository.findAll();
    }

    public Commodity getCommodityById(Long id) {
        return commodityRepository.findById(id)
                .orElseThrow(() ->
                        new CommodityNotFoundException(String.format("Commodity is not found by this id: ", id)));
    }
}
