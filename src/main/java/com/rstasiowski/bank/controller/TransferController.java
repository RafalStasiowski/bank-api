package com.rstasiowski.bank.controller;

import com.rstasiowski.bank.dto.TransferDto;
import com.rstasiowski.bank.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user/transfers")
public class TransferController {
    private TransferService transferService;

    @PostMapping("/make")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto) {
        transferService.performTransfer(transferDto);
        return ResponseEntity.ok("Transfer has been successfully registered");
    }
}
