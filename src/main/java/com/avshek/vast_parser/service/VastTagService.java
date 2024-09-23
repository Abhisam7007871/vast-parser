


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
import java.io.InputStream;
import java.net.URL;
import java.io.StringWriter;

@Service
public class VastTagService {

    @Autowired
    private VastTagRepository vastTagRepository;

    public String readXmlFromFile(String filePath) {
        try {
            File inputFile = new File(filePath);
            return readXmlContent(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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




























//package com.avshek.vast_parser.service;
//
//import com.avshek.vast_parser.model.VastTag;
//import com.avshek.vast_parser.repository.VastTagRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import java.io.File;
//import java.io.InputStream;
//import java.io.StringReader;
//import java.net.URL;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class VastTagService {
//
//    @Autowired
//    private VastTagRepository vastTagRepository;
//
//    public String readXmlFromFile(String filePath) {
//        try {
//            File inputFile = new File(filePath);
//            return readXmlContent(inputFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public String readXmlFromUrl(String url) {
//        try {
//            InputStream inputStream = new URL(url).openStream();
//            return readXmlContent(inputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private String readXmlContent(Object source) throws Exception {
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = source instanceof File ? builder.parse((File) source) : builder.parse((InputStream) source);
//
//        // Convert Document to String
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        StreamResult result = new StreamResult(new StringWriter());
//        DOMSource sourceDom = new DOMSource(doc);
//        transformer.transform(sourceDom, result);
//        return result.getWriter().toString();
//    }
//
//    public VastTag parseVastXml(String xmlContent) {
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));
//
//            // Normalize the XML structure
//            doc.getDocumentElement().normalize();
//
//            // Create the VastTag object
//            VastTag vastTag = new VastTag();
//
//            // Parse the version
//            Element vastElement = (Element) doc.getElementsByTagName("VAST").item(0);
//            vastTag.setVersion(vastElement.getAttribute("version"));
//
//            // Check for Ad element
//            NodeList adNodes = vastElement.getElementsByTagName("Ad");
//            if (adNodes.getLength() > 0) {
//                Element adElement = (Element) adNodes.item(0);
//
//                if (adElement != null) {
//                    // Check if the ID attribute exists
//                    if (adElement.hasAttribute("id")) {
//                        String id = adElement.getAttribute("id");
//                        vastTag.setId(id); // Set ID
//                    } else {
//                        System.out.println("Ad element does not have an ID attribute.");
//                    }
//
//                    // Parse additional fields like title and description
//                    NodeList titleNodes = adElement.getElementsByTagName("AdTitle");
//                    if (titleNodes.getLength() > 0) {
//                        vastTag.setTitle(titleNodes.item(0).getTextContent());
//                    }
//
//                    NodeList descriptionNodes = adElement.getElementsByTagName("Description");
//                    if (descriptionNodes.getLength() > 0) {
//                        vastTag.setDescription(descriptionNodes.item(0).getTextContent());
//                    }
//                } else {
//                    System.out.println("Ad element is null.");
//                }
//            } else {
//                System.out.println("No Ad elements found.");
//            }
//
//
//            // Parse impression
//            Element impressionElement = (Element) vastElement.getElementsByTagName("Impression").item(0);
//            VastTag.Impression impression = new VastTag.Impression();
//            impression.setId(impressionElement.getAttribute("id"));
//            impression.setUrl(impressionElement.getTextContent());
//            vastTag.setImpression(impression);
//
//            // Parse creatives
//            NodeList creatives = vastElement.getElementsByTagName("Creative");
//            List<VastTag.Creative> creativeList = new ArrayList<>();
//            for (int i = 0; i < creatives.getLength(); i++) {
//                Element creativeElement = (Element) creatives.item(i);
//                VastTag.Creative creative = new VastTag.Creative();
//                creative.setId(creativeElement.getAttribute("id"));
//
//                // Parse companion banners
//                NodeList companionBanners = creativeElement.getElementsByTagName("Companion");
//                List<VastTag.Creative.CompanionBanner> companionBannerList = new ArrayList<>();
//                for (int j = 0; j < companionBanners.getLength(); j++) {
//                    Element bannerElement = (Element) companionBanners.item(j);
//                    VastTag.Creative.CompanionBanner companionBanner = new VastTag.Creative.CompanionBanner();
//                    companionBanner.setId(bannerElement.getAttribute("id"));
//                    companionBanner.setWidth(Integer.parseInt(bannerElement.getAttribute("width")));
//                    companionBanner.setHeight(Integer.parseInt(bannerElement.getAttribute("height")));
//                    companionBanner.setType(bannerElement.getAttribute("type"));
//                    companionBanner.setSource(bannerElement.getElementsByTagName("StaticResource").item(0).getTextContent());
//                    companionBanner.setClickThroughUrl(bannerElement.getElementsByTagName("ClickThrough").item(0).getTextContent());
//                    companionBannerList.add(companionBanner);
//                }
//                creative.setCompanionBanners(companionBannerList);
//                creativeList.add(creative);
//            }
//            vastTag.setCreatives(creativeList);
//
//            return vastTag;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public VastTag saveVastTag(VastTag vastTag) {
//        return vastTagRepository.save(vastTag);
//    }
//
//    public VastTag findById(Long id) {
//        return vastTagRepository.findById(id).orElse(null);
//    }
//
//    public String convertToJson(VastTag vastTag) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.writeValueAsString(vastTag);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public VastTag saveParsedVastTag(String xmlContent) {
//        VastTag vastTag = parseVastXml(xmlContent);
//        return saveVastTag(vastTag);
//    }
//
//    public VastTag getVastTagById(Long id) {
//        return findById(id);
//    }
//}