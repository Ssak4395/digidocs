package com.example.digitaldocs.services.algorithm;

import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Vertex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class algorithms {

    int delta = 30;
    long rawABN = 0;
    public double startTotalStrategy(List<EntityAnnotation> entities) throws IOException {
        /*
        Some assumption have been made, I have decided to search semantically for the total, although a relatively naive
        approach, one needs to consider, that the word "Total" can only have so many synonyms linguistically speaking, and there is a very high chance of
        those synonyms only "Sum" would be found on a reciept

        Motivation: Given an OCR annotation determine the Total price of the items purchased

        Approach 1: We assume the word "Total" is followed by the Total sum of all purchases, this is our base case(relatively trivial).

        NOTE THIS METHOD ITSELF IS NOT PERFECT AND WILL DEFINITELY NOT FIND THE TOTAL PRICE ALL OF THE TIME

        Semantic searching can be further finetuned to find other elements from a reciept such as Store manager served, date, phone number etc.

         */
        List<EntityAnnotation> ents = entities;

        String s = "";
        Vertex v1;
        Vertex v2;
        double isConvertableValue = 0.0;


        while (isConvertableValue == 0.0) {
            for (int i = 1; i < ents.size(); ++i) {

                if (ents.get(i).getDescription().equalsIgnoreCase("Total") | ents.get(i).getDescription().contains("Total")) {
                    v1 = ents.get(i).getBoundingPoly().getVertices().get(0);
                    for (int j = i + 1; j < ents.size(); ++j) {
                        v2 = ents.get(j).getBoundingPoly().getVertices().get(0);
                        if (v1.getY() <= v2.getY() + delta && v1.getY() >= v2.getY() - delta) { // Use the delta to get surronding elements
                            try {
                                s = ents.get(j).getDescription();
                                isConvertableValue = Double.parseDouble(s.replace("$", "").trim());  // Remove the dollar sign if such exists
                                boolean check = BigDecimal.valueOf(isConvertableValue).scale() >= 2;  // most reciepts have the total in double format, this checks for that case
                                if (check) {
                                    return isConvertableValue;  // This may or may not be our total price.
                                }
                                if (isConvertableValue == 0.0) {
                                    --delta;  //If we find nothing reduce the delta until we do.
                                }
                            } catch (NumberFormatException e) {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        return isConvertableValue;
    }

    public String startABNStrategy(List<EntityAnnotation> entities) throws IOException {
        List<EntityAnnotation> ents = entities;

        Vertex v1;
        Vertex v2;

        String ABN = "";

        for (int i = 1; i < ents.size(); ++i)
            if (ents.get(i).getDescription().equalsIgnoreCase("ABN") | ents.get(i).getDescription().equalsIgnoreCase("ABN:")) {
                v1 = ents.get(i).getBoundingPoly().getVertices().get(0);
                for (int j = i + 1; j < ents.size(); ++j) {
                    v2 = ents.get(j).getBoundingPoly().getVertices().get(0);

                    if (v1.getY() <= v2.getY() + 30 && v1.getY() >= v2.getY() - 30) {
                        ABN += ents.get(j).getDescription();
                        if(ABN.toCharArray().length ==11) {
                            try {
                                rawABN = Long.parseLong(ABN);
                                return Long.toString(rawABN);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                }
            }
        if (!Long.toString(rawABN).matches( "[0-9]+")|Long.toString(rawABN).toCharArray().length != 11) {
            return "Algorithm could not detect a valid ABN";
        }
        return Long.toString(rawABN);
    }

}
