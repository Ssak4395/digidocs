package com.example.digitaldocs.services.algorithm;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class TesseractOCR  {


    public String convert(String nameOfFile) {

        // Your file name here, for example Capture.PNG
        File imageFile = new File(nameOfFile);
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping

        /*
        You need to point to your tessdata folder, here by default i have put it in our repo directory.
         */
        instance.setDatapath("C:\\Users\\Programming\\soft3888-software-project\\tessdata"); // path to tessdata directory

        String result = "";
        try {
            result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }

        return result;
    }


}
