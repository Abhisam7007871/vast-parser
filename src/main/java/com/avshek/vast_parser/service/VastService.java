package com.avshek.vast_parser.service;

import com.avshek.vast_parser.model.VastData;
import com.avshek.vast_parser.repository.VastRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class VastService {
    @Autowired
    private VastRepository vastRepository;

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
    public VastData parseVastData(String xmlContent) {
        VastData vastData = new VastData();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new StringReader(xmlContent));

            VastData.Creative currentCreative = null;
            VastData.CompanionBanner currentBanner = null;
            VastData.TrackingEvent currentTrackingEvent = null;
            VastData.MediaFile currentMediaFile = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();

                    // Parse VAST version
                    if (elementName.equals("VAST")) {
                        vastData.setVersion(startElement.getAttributeByName(new QName("version")).getValue());
                    }

                    // Parse Title
                    if (elementName.equals("AdTitle")) {
                        String elementText = eventReader.getElementText();
                        vastData.setTitle(cleanString(elementText));
                    }

                    // Parse Description
                    if (elementName.equals("Description")) {
                        String elementText = eventReader.getElementText();
                        event = eventReader.nextEvent();
                        vastData.setDescription(cleanString(elementText));
                    }

                    // Parse Impressions
                    if (elementName.equals("Impression")) {
                        VastData.Impression impression = new VastData.Impression();
                        impression.setImpressionId(startElement.getAttributeByName(new QName("id")).getValue());
                        event = eventReader.nextEvent();
                        impression.setUrl(event.asCharacters().getData());

                        if (vastData.getImpressions() == null) {
                            vastData.setImpressions(new ArrayList<>());
                        }
                        vastData.getImpressions().add(impression);
                    }

                    // Parse Creatives
                    if (elementName.equals("Creative")) {
                        currentCreative = new VastData.Creative();
                        currentCreative.setCreativeId(startElement.getAttributeByName(new QName("id")).getValue());
                        if (vastData.getCreatives() == null) {
                            vastData.setCreatives(new ArrayList<>());
                        }
                        vastData.getCreatives().add(currentCreative);
                    }

                    // Parse Companion Banners inside Creative
                    if (elementName.equals("Companion")) {
                        currentBanner = new VastData.CompanionBanner();
                        currentBanner.setBannerId(startElement.getAttributeByName(new QName("id")).getValue());
                        currentBanner.setWidth(Integer.parseInt(startElement.getAttributeByName(new QName("width")).getValue()));
                        currentBanner.setHeight(Integer.parseInt(startElement.getAttributeByName(new QName("height")).getValue()));
                        eventReader.nextTag(); // Move to child tags
                        event = eventReader.nextEvent();
                        currentBanner.setSource(event.asCharacters().getData());

                        if (currentCreative.getCompanionBanners() == null) {
                            currentCreative.setCompanionBanners(new ArrayList<>());
                        }
                        currentCreative.getCompanionBanners().add(currentBanner);
                    }

                    // Parse Tracking Events inside Creative
                    if (elementName.equals("Tracking")) {
                        currentTrackingEvent = new VastData.TrackingEvent();
                        currentTrackingEvent.setEventType(startElement.getAttributeByName(new QName("event")).getValue());
                        event = eventReader.nextEvent();
                        currentTrackingEvent.setEventUrl(event.asCharacters().getData());

                        if (currentCreative.getTrackingEvents() == null) {
                            currentCreative.setTrackingEvents(new ArrayList<>());
                        }
                        currentCreative.getTrackingEvents().add(currentTrackingEvent);
                    }

                    // Parse Media Files inside Creative
                    if (elementName.equals("MediaFile")) {
                        currentMediaFile = new VastData.MediaFile();
                        currentMediaFile.setType(startElement.getAttributeByName(new QName("type")).getValue());
                        currentMediaFile.setBitrate(Integer.parseInt(startElement.getAttributeByName(new QName("bitrate")).getValue()));
                        currentMediaFile.setWidth(Integer.parseInt(startElement.getAttributeByName(new QName("width")).getValue()));
                        currentMediaFile.setHeight(Integer.parseInt(startElement.getAttributeByName(new QName("height")).getValue()));
                        event = eventReader.nextEvent();
                        currentMediaFile.setSource(event.asCharacters().getData());

                        if (currentCreative.getMediaFiles() == null) {
                            currentCreative.setMediaFiles(new ArrayList<>());
                        }
                        currentCreative.getMediaFiles().add(currentMediaFile);
                    }

                    // Parse Video Clicks inside Creative
                    if (elementName.equals("ClickThrough")) {
                        VastData.VideoClick videoClick = new VastData.VideoClick();
                        videoClick.setClickId(startElement.getAttributeByName(new QName("id")).getValue());
                        event = eventReader.nextEvent();
                        videoClick.setUrl(event.asCharacters().getData());

                        if (currentCreative.getVideoClicks() == null) {
                            currentCreative.setVideoClicks(new ArrayList<>());
                        }
                        currentCreative.getVideoClicks().add(videoClick);
                    }
                }
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
            return null;
        }

        return vastData;
    }

    public static String cleanString(String input) {
        // Trim leading and trailing whitespace from the input string
        return input.trim();
    }

    public void saveVastData(VastData vastData) {
        vastRepository.save(vastData);
    }

    public VastData getVastDataById(Long id) {
        return vastRepository.findById(id).orElse(null);
    }

    public String convertToJson(VastData vastData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(vastData);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Log the exception
            return null; // Or handle it according to your application needs
        }
    }
}
