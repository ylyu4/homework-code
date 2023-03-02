package tw.training.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tw.training.homework.model.request.CommodityRequest;
import tw.training.homework.service.AdminService;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/commodities")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadNewCommodity(@RequestBody @Valid CommodityRequest commodityRequest) {
        adminService.createNewCommodity(commodityRequest);
    }


}
