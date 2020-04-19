package com.altimetrik.pppocr.service;

import com.altimetrik.pppocr.service.model.FieldCoordinate;
import com.altimetrik.pppocr.service.model.FieldUtil;
import com.altimetrik.pppocr.service.model.InputData;
import com.altimetrik.pppocr.service.model.OutputFormat;
import com.altimetrik.pppocr.utils.LeadtoolsLicense;
import com.altimetrik.pppocr.utils.StringCleaner;
import leadtools.codecs.RasterCodecs;
import leadtools.document.writer.*;
import leadtools.ocr.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import leadtools.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class OcrService implements OcrProgressListener {
    private static final Logger logger = LoggerFactory.getLogger(OcrService.class);
    @Autowired
    private StringCleaner stringCleaner;

    // Data used when running. Parsed from the command line

    public void onProgress(OcrProgressData data){
        logger.info(data.getPercentage() + "%");
    }

    public boolean loadLibraries () {
        try {
            Platform.setLibPath(LeadtoolsLicense.getLibPath());

            Platform.loadLibrary(LTLibrary.LEADTOOLS);
            Platform.loadLibrary(LTLibrary.CODECS);
            Platform.loadLibrary(LTLibrary.IMAGE_PROCESSING_COLOR);
            Platform.loadLibrary(LTLibrary.IMAGE_PROCESSING_CORE);
            Platform.loadLibrary(LTLibrary.IMAGE_PROCESSING_EFFECTS);
            Platform.loadLibrary(LTLibrary.DOCUMENT);
            Platform.loadLibrary(LTLibrary.SVG);
            Platform.loadLibrary(LTLibrary.PDF);
            Platform.loadLibrary(LTLibrary.DOCUMENT_WRITER);
            Platform.loadLibrary(LTLibrary.OCR);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private boolean setlanguage(OcrEngine ocrEngine, InputData demoData) {
        return true;
    }
    public Map<String,String> process(MultipartFile file){

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Parse the command line and verify it
        logger.info(fileName);
        logger.info(file.getContentType());

        InputData data = new InputData();
        data.inFilePath= "D:\\altimetrik\\IRS 941 - Q1-2020.pdf";
        Map<String,String> results = new HashMap<>();

        try {
            // Load LEADTOOLS libraries
            if (!loadLibraries())
                return results;

            // Set the license
            if (!LeadtoolsLicense.setLicense()) {
                logger.info("Please Set Your Runtime License...\nExiting Demo...");
                return results;
            }

            // Set the Shadow Fonts
            LeadtoolsLicense.setShadowFonts();



            boolean useDocument = data.outputFormat != null;

            // Show the values:
            logger.info("inFilePath: " + data.inFilePath);
//         logger.info("engineDirectory: " + data.engineDirectory);
//         logger.info("firstPage: " + data.firstPage);
//         logger.info("lastPage: " + data.lastPage);
            // Run the demo
            OcrEngine ocrEngine = null;
            OcrDocument ocrDocument = null;
            DocumentFormat documentFormat = null;

            try {
                // Start the OCR engine
                logger.info("Starting OCR engine...");
                ocrEngine = OcrEngineManager.createEngine(OcrEngineType.LEAD);
                ocrEngine.startup(null, null, null, data.engineDirectory);

                // The RasterCodecs object to use when loading images
                RasterCodecs rasterCodecs = ocrEngine.getRasterCodecsInstance();

                // Create a LEAD stream from the input file
                ILeadStream stream = LeadStreamFactory.create(file.getBytes());

                // Get the number of pages in the input file
                int pageCount = rasterCodecs.getTotalPages(stream);

                // Verify user first/last page values are in range
                if (data.firstPage == 0) data.firstPage = 1;
                if (data.lastPage == -1) data.lastPage = pageCount;

                if (useDocument) {
                    // Set the output document format options and get the corresponding LEADTOOLS DocumentFormat enumeration member
                    documentFormat = setOutputDocumentFormatOptions(ocrEngine.getDocumentWriterInstance(), data.outputFormat);

                    // Create an OCR document (to be used with saving)
                    ocrDocument = ocrEngine.getDocumentManager().createDocument(null, OcrCreateDocumentOptions.AUTO_DELETE_FILE.getValue());
                }

                // Set the language
                if (setlanguage(ocrEngine, data)) {
                    // Process the pages
                    RasterImage rasterImage = null;
                    OcrPage ocrPage = null;
                    int pageNum =-1;
                    for (int pageNumber = 1; pageNumber <= pageCount; pageNumber++) {


                        try {
                            // Load the raster image for this page
                            logger.info("Loading page number " + pageNumber);
                            rasterImage = rasterCodecs.load(stream, pageNumber);
                            // Create an OcrPage from it, we do not need the image anymore, so use AUTO_DISPOSE so the page will take care of it
                            ocrPage = ocrEngine.createPage(rasterImage,  OcrImageSharingMode.AUTO_DISPOSE);
                            // Since we used AUTO_DISPOSE, we should not use rasterImage anymore, it belongs to the page
                            rasterImage = null;


                            // Create new OcrZone
                            OcrZone ocrZone = new OcrZone();

                            // Set ocrZone's bound
                            ocrZone.setBounds(new LeadRect(2150,
                                    100,
                                    300,
                                    100));
                            ocrZone.setZoneType(OcrZoneType.TEXT);
                            // Add ocrZone to the ocrPage
                            logger.info("Add zone to the page");
                            ocrPage.getZones().add(ocrZone);


                            // Recognize the page
                            logger.info("Recognizing page");
                            ocrPage.recognize(this);
                            String value = ocrPage.getText(-1);
                            logger.info(value);
                            if(value!=null) {
                                if (value.contains("950117")) {
                                    pageNum = pageNumber;
                                }
                            }

                        }
                        finally {
                            // Clean up, we do not need the page anymore. The recognition data has been saved into the document, so delete it
                            if (ocrPage != null)
                                ocrPage.dispose();

                            // In case something went wrong, dispose the image
                            if (rasterImage != null)
                                rasterImage.dispose();
                        }
                    }
                    if(pageNum == -1){
                        logger.info("couldn't found Employer’s QUARTERLY Federal Tax Return page");
                        return results;
                    }

                    try {

                        logger.info("-------------------RECOGNIZE------------------------------");
                        logger.info("REPORTPAGE :" +pageNum);
                        for (String fieldName : FieldUtil.getFieldUtil().getFieldCoordinates().keySet())
                        {
                            FieldCoordinate fieldCoordinate = FieldUtil.getFieldUtil().getFieldCoordinates().get(fieldName);
                            // Load the raster image for this page
                            rasterImage = rasterCodecs.load(stream, pageNum);
                            // Create an OcrPage from it, we do not need the image anymore, so use AUTO_DISPOSE so the page will take care of it
                            ocrPage = ocrEngine.createPage(rasterImage,  OcrImageSharingMode.AUTO_DISPOSE);
                            // Since we used AUTO_DISPOSE, we should not use rasterImage anymore, it belongs to the page
                            rasterImage = null;


                            OcrZone ocrZone = new OcrZone();
                            ocrZone.setZoneType(OcrZoneType.forValue(fieldCoordinate.getZoneType()));
                            ocrZone.setBounds(new LeadRect(
                                    fieldCoordinate.getXposition(),
                                    fieldCoordinate.getYposition(),
                                    fieldCoordinate.getWidth(),
                                    fieldCoordinate.getHeight()));
                            ocrPage.getZones().add(ocrZone);

                            ocrPage.recognize(this);
                            String value = ocrPage.getText(-1);
                            logger.info(fieldName + " = " + value);
                            results.put(fieldName, stringCleaner.clearChar(value));

                        }
                        logger.info("-------------------DONE------------------------------");
                        logger.info(results.toString());


                    }
                    finally {
                        // Clean up, we do not need the page anymore. The recognition data has been saved into the document, so delete it
                        if (ocrPage != null)
                            ocrPage.dispose();

                        // In case something went wrong, dispose the image
                        if (rasterImage != null)
                            rasterImage.dispose();
                    }

                    if (useDocument) {
                        // At this point, all the pages have been added to the document, save it to the final format
                        logger.info("Saving the document to " + data.outFilePath);
                        ocrDocument.save(data.outFilePath, documentFormat, null);
                    }

                    logger.info("Finished");
                    return results;
                }
            } finally {
                // Clean up by disposing of the objects we created
                if (ocrDocument != null)
                    ocrDocument.dispose();

                if (ocrEngine != null)
                    ocrEngine.dispose();
            }
        }
        catch (Exception ex) {
            logger.info("Error " + ex.getMessage());
            ex.printStackTrace();
        }
        return results;
    }

    private DocumentFormat setOutputDocumentFormatOptions(DocumentWriter documentWriter, OutputFormat outputFormat) {
        DocumentFormat documentFormat;

        switch (outputFormat) {
            case PDF:
            case PDF_EMBED:
            case PDFA:
            case PDF_IMAGE_OVER_TEXT:
            case PDF_EMBED_IMAGE_OVER_TEXT:
            case PDFA_IMAGE_OVER_TEXT:
                // PDF, set extra options
                documentFormat = DocumentFormat.PDF;
                PdfDocumentOptions pdfOptions = (PdfDocumentOptions)documentWriter.getOptions(documentFormat);

                // Set PDF Type
                if(outputFormat == OutputFormat.PDFA || outputFormat == OutputFormat.PDFA_IMAGE_OVER_TEXT) {
                    pdfOptions.setDocumentType(PdfDocumentType.PDFA);
                }
                else {
                    pdfOptions.setDocumentType(PdfDocumentType.PDF);
                }

                // Set FontEmbed Value
                if (outputFormat == OutputFormat.PDF_EMBED || outputFormat == OutputFormat.PDF_EMBED_IMAGE_OVER_TEXT) {
                    pdfOptions.setFontEmbedMode(DocumentFontEmbedMode.ALL);
                }

                // Set ImageOverText Value
                pdfOptions.setImageOverText(outputFormat == OutputFormat.PDF_IMAGE_OVER_TEXT || outputFormat == OutputFormat.PDF_EMBED_IMAGE_OVER_TEXT || outputFormat == OutputFormat.PDFA_IMAGE_OVER_TEXT);
                break;

            case DOCX:
            case DOCX_FRAMED:
                // DOCX, set extra options
                documentFormat = DocumentFormat.DOCX;
                DocxDocumentOptions docxOptions = (DocxDocumentOptions)documentWriter.getOptions(documentFormat);
                docxOptions.setTextMode(outputFormat == OutputFormat.DOCX_FRAMED ? DocumentTextMode.FRAMED : DocumentTextMode.NON_FRAMED);
                break;

            case RTF:
            case RTF_FRAMED:
                // RTF, set extra options
                documentFormat = DocumentFormat.RTF;
                RtfDocumentOptions rtfOptions = (RtfDocumentOptions)documentWriter.getOptions(documentFormat);
                rtfOptions.setTextMode(outputFormat == OutputFormat.RTF_FRAMED ? DocumentTextMode.FRAMED : DocumentTextMode.NON_FRAMED);
                break;

            case TEXT:
            case TEXT_FORMATTED:
                // TXT, set extra options
                documentFormat = DocumentFormat.TEXT;
                TextDocumentOptions textOptions = (TextDocumentOptions)documentWriter.getOptions(documentFormat);
                textOptions.setDocumentType(TextDocumentType.UTF8);
                textOptions.setFormatted(outputFormat == OutputFormat.TEXT_FORMATTED);
                break;

            case SVG:
                // SVG, no extra options
                documentFormat = DocumentFormat.SVG;
                break;

            case ALTO_XML:
                // ALTO XML, no extra options
                documentFormat = DocumentFormat.ALTO_XML;
                break;

            case HTM:
                documentFormat = DocumentFormat.HTML;
                break;

            case MOBI:
                documentFormat = DocumentFormat.MOB;
                break;

            case EPUB:
                documentFormat = DocumentFormat.PUB;
                break;

            default:
                // Something went wrong
                throw new IllegalStateException("Invalid format " + outputFormat);
        }

        return documentFormat;
    }
}
