package com.example.digitaldocs.services.algorithm;

import java.io.IOException;

public class Application {


    public static void main(String args[]) throws IOException {
        TextFacadeImpl textFacade = new TextFacadeImpl();

        /*
        IMPORTANT PLEASE ACCESS THE TESSERACT OCR CLASS FIRST, YOU NEED TO POINT TO THE TRAINING DATA.
        ALSO MAKE SURE YOU SET YOUR ENVIRONMENT VARIABLE POINTING TO MY API KEY OR ELSE YOU CANNOT RUN THE GOOGLE OCR API.
        */


        // Point to file normally.
        textFacade.convertTesseract("Capture.PNG");
       //Point to file using absolute path.
       // textFacade.convertGoogleCloudVisionAPI("C:\\Users\\Programming\\Desktop\\TextDetection\\src\\main\\resources\\Capture.PNG");
    }
}
