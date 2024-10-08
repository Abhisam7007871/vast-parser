package com.avshek.vast_parser.parser;

import com.avshek.vast_parser.model.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.InputSource;

public class VastParser {

    public VastData parseVastXmlFromString(String xmlContent) {
        return parseVastXml(new InputSource(new StringReader(xmlContent)));
    }

    public VastData parseVastXmlFromUrl(String url) throws Exception {
        InputStream inputStream = new URL(url).openStream();
        return parseVastXml(new InputSource(inputStream));
    }

    private VastData parseVastXml(InputSource inputSource) {
        VastData vastTag = new VastData();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputSource);

            Element root = doc.getDocumentElement();
            vastTag.setVersion(root.getAttribute("version"));

            NodeList adList = doc.getElementsByTagName("Ad");
            if (adList.getLength() > 0) {
                Element adElement = (Element) adList.item(0);
                vastTag.setVastId(adElement.getAttribute("id"));
                vastTag.setTitle(getTextContent(adElement, "AdTitle"));
                vastTag.setDescription(getTextContent(adElement, "Description"));

                // Parse Impression


                // Parse Creatives
                NodeList creativeList = adElement.getElementsByTagName("Creative");
                List<Creative> creatives = new ArrayList<>();
                for (int i = 0; i < creativeList.getLength(); i++) {
                    Element creativeElement = (Element) creativeList.item(i);
                    Creative creative = new Creative();
                    creative.setVastTag(vastTag);
                    String creativeId = creativeElement.getAttribute("id");
                    if (!creativeId.isEmpty()) {
                        creative.setId(Long.parseLong(creativeId));
                    }

                    // Check for CompanionAds
                    NodeList companionAdsList = creativeElement.getElementsByTagName("CompanionAds");
                    if (companionAdsList.getLength() > 0) {
                        Element companionAdsElement = (Element) companionAdsList.item(0);
                        NodeList companionList = companionAdsElement.getElementsByTagName("Companion");
                        List<CompanionBanner> companionBanners = new ArrayList<>();
                        for (int j = 0; j < companionList.getLength(); j++) {
                            Element companionElement = (Element) companionList.item(j);
                            CompanionBanner companionBanner = new CompanionBanner();
                            companionBanner.setCreative(creative);

                            String companionId = companionElement.getAttribute("id");
                            if (!companionId.isEmpty()) {
                                companionBanner.setId(Long.parseLong(companionId));
                            }
                            companionBanner.setWidth(Integer.parseInt(companionElement.getAttribute("width")));
                            companionBanner.setHeight(Integer.parseInt(companionElement.getAttribute("height")));

                            // Get StaticResource and CompanionClickThrough
                            companionBanner.setSource(getTextContent(companionElement, "StaticResource"));
                            companionBanner.setClickThroughUrl(getTextContent(companionElement, "CompanionClickThrough"));
                            companionBanners.add(companionBanner);
                        }
                        creative.setCompanionBanners(companionBanners);
                    }

                    // Check for Linear
                    NodeList linearList = creativeElement.getElementsByTagName("Linear");
                    if (linearList.getLength() > 0) {
                        Element linearElement = (Element) linearList.item(0);
                        String durationStr = getTextContent(linearElement, "Duration");
                        if (durationStr != null) {
                            creative.setDuration(parseDuration(durationStr));
                        }
                    }

                    creatives.add(creative);
                }
                vastTag.setCreatives(creatives);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vastTag;
    }

    private String getTextContent(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent().trim();
        }
        return null;
    }

    private int parseDuration(String durationStr) {
        // Duration format is HH:MM:SS or HH:MM:SS.mmm
        String[] parts = durationStr.split(":");
        if (parts.length != 3) {
            return 0;
        }
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        float seconds = Float.parseFloat(parts[2]);
        return (int) ((hours * 3600) + (minutes * 60) + seconds);
    }
}
