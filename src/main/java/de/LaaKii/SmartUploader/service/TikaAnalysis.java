package de.LaaKii.SmartUploader.service;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class TikaAnalysis {

    public static String detectDocType(InputStream stream){
        Detector detector = new DefaultDetector();
        Metadata metadata = new Metadata();
        MediaType docType = null;
        try {
             docType = detector.detect(stream, metadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docType.toString();
    }

    public static String extractContent(InputStream stream){
        Parser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        try {
            parser.parse(stream, handler, metadata, context);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
        return handler.toString();
    }

    public static List<String> checkForFittingFolders(List<String> folderNames, String content){
        return folderNames.stream().filter(f -> content.toLowerCase().contains(f)).collect(Collectors.toList());
    }
}
