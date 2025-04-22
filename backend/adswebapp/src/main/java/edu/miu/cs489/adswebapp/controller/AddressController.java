package edu.miu.cs489.adswebapp.controller;

import edu.miu.cs489.adswebapp.dto.response.AddressResponseDTO;
import edu.miu.cs489.adswebapp.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;


    @GetMapping
    public ResponseEntity<Page<AddressResponseDTO>> getAddresses(
            @RequestParam(value = "page", defaultValue = "0") int page,

            @RequestParam(value = "size", defaultValue = "5") int size
                                                                ) {
        return ResponseEntity.ok(addressService.findAll(PageRequest.of(page, size)));
    }
}
