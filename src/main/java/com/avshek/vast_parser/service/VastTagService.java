


package com.avshek.vast_parser.service;

import com.avshek.vast_parser.model.VastTag;
import com.avshek.vast_parser.repository.VastTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class VastTagService {

    @Autowired
    private VastTagRepository vastTagRepository;

    public String readXmlFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null or handle the error appropriately
        }
    }

    public String readXmlFromUrl(String url) {
        try {
            InputStream inputStream = new URL(url).openStream();
            return readXmlContent(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readXmlContent(Object source) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = source instanceof File ? builder.parse((File) source) : builder.parse((InputStream) source);

        // Convert Document to String
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource sourceDom = new DOMSource(doc);
        transformer.transform(sourceDom, result);
        return result.getWriter().toString();
    }

    public VastTag parseVastXml(String xmlContent) {
        // Implement your parsing logic here
        return new VastTag(); // Placeholder
    }

    public VastTag saveVastTag(VastTag vastTag) {
        return vastTagRepository.save(vastTag);
    }

    public VastTag getVastTagById(Long id) {
        return findById(id);
    }

    public String convertToJson(VastTag vastTag) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(vastTag);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public VastTag findById(Long id) {
        return vastTagRepository.findById(id).orElse(null); // This should work if the repository is set up correctly
    }
}


