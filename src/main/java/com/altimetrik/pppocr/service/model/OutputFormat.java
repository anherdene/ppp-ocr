package com.altimetrik.pppocr.service.model;

// Supported output document formats
// LEADTOOLS uses DocumentFormat enumeration with options, this demo
// creates a simplified enumeration then convert it to DocumentFormat and set the options
public enum OutputFormat {
    PDF,
    PDF_EMBED,
    PDFA,
    PDF_IMAGE_OVER_TEXT,
    PDF_EMBED_IMAGE_OVER_TEXT,
    PDFA_IMAGE_OVER_TEXT,
    DOCX,
    DOCX_FRAMED,
    RTF,
    RTF_FRAMED,
    TEXT,
    TEXT_FORMATTED,
    SVG,
    ALTO_XML,
    HTM,
    EPUB,
    MOBI
}
