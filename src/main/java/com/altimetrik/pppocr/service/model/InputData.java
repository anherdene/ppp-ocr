package com.altimetrik.pppocr.service.model;

import leadtools.LeadRect;

public class InputData {
    public String inFilePath;           // Input file name - the image file to be recognized
    public String outFilePath;          // Output file name - the result file created by this demo, Optional argument, not needed when outputFormat is not used
    public OutputFormat outputFormat;   // Output file format, such as PDF or DOCX, etc. Optional argument, if this argument is not used, the result will be printed out as text into the console.
    public String engineDirectory;      // Path to LEADTOOLS OCR runtime directory
    public LeadRect zoneBounds;         // Zone Bounds - This optional argument sets zone bounds, which will be used only on the first page of the document. All other pages (if any) will be auto-zoned
    public int firstPage;               // First page number to process in inFilePath. Optional, default is 1
    public int lastPage;                // Last page number to process in inFilePath. Optional, default is -1 means all pages

}
