package de.LaaKii.SmartUploader.service;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import java.io.IOException;
import java.io.InputStream;

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
}
