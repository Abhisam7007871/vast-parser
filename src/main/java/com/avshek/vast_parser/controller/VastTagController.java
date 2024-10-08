







package com.avshek.vast_parser.controller;

import com.avshek.vast_parser.model.VastData;
import com.avshek.vast_parser.service.VastTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/vast")
public class VastTagController {

    @Autowired
    private VastTagService vastTagService;

    @PostMapping("/parse/file")
    public ResponseEntity<String> parseVastFile(@RequestParam String filePath) throws FileNotFoundException {
        String xmlContent = vastTagService.readXmlFromFile(filePath);
        if (xmlContent == null) {
            return ResponseEntity.badRequest().body("Error reading XML file.");
        }
        VastData vastData = vastTagService.parseVastXml(xmlContent);
        vastTagService.saveVastTag(vastData);
        String jsonResponse = vastTagService.convertToJson(vastData);
        return ResponseEntity.ok(jsonResponse);
    }

    @PostMapping("/parse/url")
    public ResponseEntity<String> parseVastUrl(@RequestParam String url) {
        String xmlContent = vastTagService.readXmlFromUrl(url);
        if (xmlContent == null) {
            return ResponseEntity.badRequest().body("Error reading XML from URL.");
        }
        VastData vastData = vastTagService.parseVastXml(xmlContent);
        vastTagService.saveVastTag(vastData);
        String jsonResponse = vastTagService.convertToJson(vastData);
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VastData> getVastTagById(@PathVariable Long id) {
        VastData vastData = vastTagService.findById(id);
        return vastData != null ? ResponseEntity.ok(vastData) : ResponseEntity.notFound().build();
    }
}


























