package com.codeittoday.dev_checkpoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.codeittoday.dev_checkpoint.dto.FormRequest;
import com.codeittoday.dev_checkpoint.service.ExcelService;

@RestController
@RequestMapping("/api/excel")
@CrossOrigin("*")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateExcel(@RequestBody FormRequest request) {
        try {

            byte[] excel = excelService.generateExcel(request);
            System.out.println(excel);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition
                    .attachment()
                    .filename("report.xlsx")
                    .build());

            return new ResponseEntity<>(excel, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}