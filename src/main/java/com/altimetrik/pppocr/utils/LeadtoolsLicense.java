package com.altimetrik.pppocr.utils;

import leadtools.LEADResourceDirectory;
import leadtools.Platform;
import leadtools.RasterDefaults;
import leadtools.RasterSupport;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Paths;

@Component
public class LeadtoolsLicense {
    public static boolean setLicense() {
        if (RasterSupport.getKernelExpired()) {
            try {
                // TODO: Replace these with the path to the LEADTOOLS license file
                String licenseFilePath = "D:\\altimetrik\\eval-license-files_323c5d99-fd68-4fdf-a481-1a1ffce2bf09\\eval-license-files.lic";
                String developerKey = "g8wbXs5T3paWbQfDR9Ezk4CRX8AdLmAI6d383qJp6jPooJbYamOe1y+Yx3r6CmFEapzEcruaaySEpO5Gp+GYlxkjGqF8n07u";

                if (licenseFilePath == null) {
                    String commonLicenseDir = new File(LeadtoolsLicense.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
                    commonLicenseDir = Paths.get(commonLicenseDir, "../../../Support/Common/License").toFile().getCanonicalPath().toString();
                    File commonLicenseDirFile = new File(commonLicenseDir);
                    if (commonLicenseDirFile.exists() && commonLicenseDirFile.isDirectory()) {
                        File commonLicenseFilePath = Paths.get(commonLicenseDir, "LEADTOOLS.lic").toFile();
                        File commonDeveloperKeyFilePath = Paths.get(commonLicenseDir, "LEADTOOLS.lic.key").toFile();

                        boolean commonLicenseFileFound = commonLicenseFilePath.exists();
                        boolean commonDeveloperKeyFileFound = commonDeveloperKeyFilePath.exists();
                        if (commonLicenseFileFound && commonDeveloperKeyFileFound) {
                            licenseFilePath = commonLicenseFilePath.toString();
                            BufferedReader br = new BufferedReader(new FileReader(commonDeveloperKeyFilePath));
                            try {
                                developerKey =br.readLine();
                            } finally {
                                br.close();
                            }
                        } else {
                            if (!commonLicenseFileFound)
                                System.out.println(String.format("License file not found:\n%s", commonLicenseFilePath.toString()));
                            if (!commonDeveloperKeyFileFound)
                                System.out.println(String.format("Developer key file not found:\n%s", commonDeveloperKeyFilePath.toString()));
                        }
                    } else {
                        System.out.println(String.format("Common license directory does not exit:\n%s", commonLicenseDir));
                    }
                }

                if (licenseFilePath != null && developerKey != null)
                    RasterSupport.setLicense(licenseFilePath, developerKey);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }

        return !RasterSupport.getKernelExpired();
    }

    public static String getLibPath() throws IOException {
        File libDirectory = new File (LeadtoolsLicense.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
        String libPath = URLDecoder.decode(libDirectory.getAbsolutePath(), "UTF-8");

        if(Platform.isWindows()) {
            libPath += "/../CDLL/";
            if(Platform.is64Bit())
                libPath += "x64";
            else
                libPath += "Win32";
        } else if(Platform.isLinux()){
            libPath += "/../Lib/";
            if(Platform.is64Bit())
                libPath += "x64";
            else
                libPath += "x86";
        }
        else if(Platform.isMac()){
            libPath += "/../Frameworks/macOS";
        }
        // TODO Change dynimac uri
        libPath = "D:\\LEADTOOLS 20\\Redist\\CDLL\\x64";
        File file = new File(libPath);
        if (file.exists()) {
            libPath = file.getCanonicalPath();
        }


        System.out.println("LEADTOOLS libPath: " + libPath);
        return libPath;
    }

    public static String getOcrEngineRuntimePath() throws IOException {
        String ocrEngineRuntimePath = null;
        File libDirectory = new File (LeadtoolsLicense.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
        String libPath = URLDecoder.decode(libDirectory.getAbsolutePath(), "UTF-8");
        String ocrRelativePath = "/../Common/OcrLEADRuntime/";

        ocrEngineRuntimePath = libPath + ocrRelativePath;
        File file = new File(ocrEngineRuntimePath);
        if (file.exists()) {
            ocrEngineRuntimePath = file.getCanonicalPath();
        }

        return ocrEngineRuntimePath;
    }

    public static void setShadowFonts() {
        try {
            if (!Platform.isWindows()) {
                File libDirectory = new File (LeadtoolsLicense.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
                String shadowFontsPath = URLDecoder.decode(libDirectory.getAbsolutePath(), "UTF-8");
                shadowFontsPath += "/../Common/ShadowFonts/";
                File file = new File(shadowFontsPath);
                if (!file.exists()) {
                    shadowFontsPath = file.getCanonicalPath();
                    System.out.println(String.format("Unable to set shadow fonts because the directory %s does not exist. The demo will continue to run as normal but OCR and Document Writer operations might produce results with less than optimal accuracy.", shadowFontsPath));
                    return;
                }

                RasterDefaults.setResourceDirectory(LEADResourceDirectory.FONTS, shadowFontsPath);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean isNullOrEmpty(String stringText) {
        return stringText == null || stringText.equals("");
    }
}
