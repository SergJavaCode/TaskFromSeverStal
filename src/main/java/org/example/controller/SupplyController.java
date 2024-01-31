package org.example.controller;

import org.example.dto.ReportDto;
import org.example.dto.SupplyCreateDto;
import org.example.dto.SupplyDto;
import org.example.service.SupplyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SupplyController {

    private final SupplyService service;

    public SupplyController(SupplyService service) {
        this.service = service;
    }

    @PostMapping("/create-supply")
    public ResponseEntity<SupplyDto> createSupply(@RequestBody SupplyCreateDto req) {
        return ResponseEntity.ok(
                new SupplyDto(service.createSupply(req))
        );
    }

    @GetMapping(value = "/report")
    public ResponseEntity<List<ReportDto>> getReport(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                     @RequestParam LocalDate from,
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                     @RequestParam LocalDate to) {
        List<ReportDto> reports = service.getReportByPeriod(from, to)
                .stream()
                .map(ReportDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reports);
    }


}
