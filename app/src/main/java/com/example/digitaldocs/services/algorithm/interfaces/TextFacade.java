package interfaces;

import java.io.IOException;

public interface TextFacade {

    String convertTesseract(String pathToFile);
    void convertGoogleCloudVisionAPI(String pathToFile) throws IOException;
}
