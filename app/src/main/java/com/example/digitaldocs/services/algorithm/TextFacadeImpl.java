package com.example.digitaldocs.services.algorithm;

import interfaces.TextFacade;

import java.io.IOException;

public class TextFacadeImpl implements TextFacade {


        private TesseractOCR tesseractOCR;
        private DetectText googleOCR;








        public TextFacadeImpl()
        {
            tesseractOCR = new TesseractOCR();
            googleOCR = new DetectText();

        }

    @Override
    public String convertTesseract(String pathToFile) {

            return tesseractOCR.convert(pathToFile);
    }


    @Override
    public void convertGoogleCloudVisionAPI(String pathToFile) throws IOException {
         googleOCR.detectText(pathToFile);
    }
}
