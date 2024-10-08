package com.avshek.vast_parser.controller;

import com.avshek.vast_parser.model.VastData;
import com.avshek.vast_parser.service.VastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vast")
public class VastController {
    @Autowired
    private VastService vastService;

    @PostMapping("/parse/file")
    public String readFromFile(@RequestParam String filePath) {
        String xmlContent = vastService.readXmlFromFile(filePath);
        if (xmlContent != null) {
            VastData vastData = vastService.parseVastData(xmlContent);
            vastService.saveVastData(vastData);
            return vastService.convertToJson(vastData);
        }
        return "Error reading file.";
    }

    @PostMapping("/parse/url")
    public String readFromUrl(@RequestParam String url) {
        String xmlContent = vastService.readXmlFromUrl(url);
        if (xmlContent != null) {
            VastData vastData = vastService.parseVastData(xmlContent);
            vastService.saveVastData(vastData);
            return vastService.convertToJson(vastData);
        }
        return "Error reading URL.";
    }

    @GetMapping("/{id}")
    public VastData getVastData(@PathVariable Long id) {
        return vastService.getVastDataById(id);
    }
}
