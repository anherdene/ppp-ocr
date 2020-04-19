package com.altimetrik.pppocr.controller;

import com.altimetrik.pppocr.service.OcrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ppp/ocr")
public class OcrController {
    private static final Logger logger = LoggerFactory.getLogger(OcrController.class);
    @Autowired
    OcrService ocrService;

    @GetMapping("/home")
    public ResponseEntity getHome(){
        return ResponseEntity.ok("succes");
    }

//    @PostMapping("/home")
//    public ResponseEntity getHome(F){
//        return ResponseEntity.ok(ocrService.process());
//    }

    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestParam("ocrfile") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        return ResponseEntity.ok(ocrService.process(file));
    }

}
