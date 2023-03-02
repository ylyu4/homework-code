package tw.training.homework.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.training.homework.exception.CommodityDuplicateException;
import tw.training.homework.model.Commodity;
import tw.training.homework.model.Image;
import tw.training.homework.model.Order;
import tw.training.homework.model.Price;
import tw.training.homework.model.request.CommodityRequest;
import tw.training.homework.repository.CommodityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CommodityRepository commodityRepository;


    public void createNewCommodity(CommodityRequest commodityRequest) {
        String sku = commodityRequest.getSku();
        validateSku(sku);
        List<Image> images = commodityRequest.getImages().stream().map(Image::new).collect(Collectors.toList());
        Price price = new Price(commodityRequest.getPrice());
        Commodity commodity = new Commodity(commodityRequest.getSku(), commodityRequest.getTitle(),
                commodityRequest.getDescription(), price, images);

        commodityRepository.save(commodity);
    }

    private void validateSku(String sku) {
        Optional<Commodity> optionalCommodity = commodityRepository.findBySku(sku);
        if (optionalCommodity.isPresent()) {
            throw new CommodityDuplicateException(String.format("The sku: %s is duplicated.", sku));
        }
    }


}
