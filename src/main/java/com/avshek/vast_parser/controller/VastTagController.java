







package com.avshek.vast_parser.controller;

import com.avshek.vast_parser.model.VastTag;
import com.avshek.vast_parser.service.VastTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vast")
public class VastTagController {

    @Autowired
    private VastTagService vastTagService;

    @PostMapping("/parse/file")
    public ResponseEntity<String> parseVastFile(@RequestParam String filePath) {
        String xmlContent = vastTagService.readXmlFromFile(filePath);
        if (xmlContent == null) {
            return ResponseEntity.badRequest().body("Error reading XML file.");
        }
        VastTag vastTag = vastTagService.parseVastXml(xmlContent);
        vastTagService.saveVastTag(vastTag);
        String jsonResponse = vastTagService.convertToJson(vastTag);
        return ResponseEntity.ok(jsonResponse);
    }

    @PostMapping("/parse/url")
    public ResponseEntity<String> parseVastUrl(@RequestParam String url) {
        String xmlContent = vastTagService.readXmlFromUrl(url);
        if (xmlContent == null) {
            return ResponseEntity.badRequest().body("Error reading XML from URL.");
        }
        VastTag vastTag = vastTagService.parseVastXml(xmlContent);
        vastTagService.saveVastTag(vastTag);
        String jsonResponse = vastTagService.convertToJson(vastTag);
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VastTag> getVastTagById(@PathVariable Long id) {
        VastTag vastTag = vastTagService.findById(id);
        return vastTag != null ? ResponseEntity.ok(vastTag) : ResponseEntity.notFound().build();
    }
}




























//package com.avshek.vast_parser.controller;
//
//import com.avshek.vast_parser.model.VastTag;
//import com.avshek.vast_parser.service.VastTagService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/vast")
//public class VastTagController {
//
//    @Autowired
//    private VastTagService vastTagService;
//
//    @PostMapping("/parse/file")
//    public ResponseEntity<String> parseVastFile(@RequestParam String filePath) {
//        String xmlContent = vastTagService.readXmlFromFile(filePath);
//        if (xmlContent == null) {
//            return ResponseEntity.badRequest().body("Error reading XML file.");
//        }
//        VastTag vastTag = vastTagService.parseVastXml(xmlContent);
//        vastTagService.saveVastTag(vastTag);
//        return ResponseEntity.ok("VAST file parsed and saved.");
//    }
//
//    @PostMapping("/parse/url")
//    public ResponseEntity<String> parseVastUrl(@RequestParam String url) {
//        String xmlContent = vastTagService.readXmlFromUrl(url);
//        if (xmlContent == null) {
//            return ResponseEntity.badRequest().body("Error reading XML from URL.");
//        }
//        VastTag vastTag = vastTagService.parseVastXml(xmlContent);
//        vastTagService.saveVastTag(vastTag);
//        return ResponseEntity.ok("VAST URL parsed and saved.");
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<VastTag> getVastTagById(@PathVariable Long id) {
//        VastTag vastTag = vastTagService.findById(id);
//        return vastTag != null ? ResponseEntity.ok(vastTag) : ResponseEntity.notFound().build();
//    }
//}