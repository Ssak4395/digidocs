package com.example.digitaldocs.services.algorithm;

import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Vertex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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



    public ArrayList<ArrayList<String>> generateLineItems(List<EntityAnnotation> entityAnnotations) throws IOException {

        // We dont need to touch bounding box coordinates because we are going on the assumption that the user will accurately
        List<EntityAnnotation> ents = entityAnnotations;
        ArrayList<String> lineItems = new ArrayList<>();
        ArrayList<String> linePrices = new ArrayList<>();


        String description =   ents.get(0).getDescription();
        String[] arr = description.split("\\r?\\n");
        ArrayList<String> sentence = new ArrayList<String>(Arrays.asList(arr));


        //Lets save and store the prices for all the items.

        //Remove any miscellanous words, usually these consist of line items that have less than 6 words, since the probability of this being a item in a reciept is extremely low
        // Rather it could be an OCR malfunction,



        for(int i = 0; i<sentence.size(); ++i) {
            try {
                double price = Double.parseDouble(sentence.get(i).replace("$",""));
                linePrices.add(sentence.get(i));

            } catch (NumberFormatException e) {
            }
        }
        // Stage 2 we now have removed all the prices from our line items, we are not left with Line Items only, however further processing still needs to be done.
        for(int i = 0; i<sentence.size(); ++i)
        {
            for(int j = 0; j<linePrices.size(); ++j)
            {
                if(sentence.get(i).equals(linePrices.get(j)))
                {
                    sentence.remove(i);
                }
            }
        }

        for(int i = 0; i<sentence.size(); ++i)
        {
            if(sentence.get(i).contains("@") | sentence.get(i).contains("Quantity") | sentence.get(i).contains("NET"))
            {
                sentence.remove(i);
            }
            if(sentence.get(i).length() < 6)
            {
                sentence.remove(i);
            }

        }
        if(sentence.size() - linePrices.size() == 1)
        {
            sentence.remove(sentence.size()-1);
        }
        if(isParseable(sentence.size(), linePrices.size()))
        {
            lineItems = sentence;
            for(int i = 0; i<lineItems.size(); ++i)
            {
                System.out.println("Item Name: " + lineItems.get(i) +" " + "Item Price: " + "$" + linePrices.get(i));
            }

        }
        for(int i = 0; i<lineItems.size(); ++i)
        {
            System.out.println(lineItems.get(i));
        }
        for(int i = 0; i<linePrices.size(); ++i)
        {
            System.out.println(linePrices.get(i));
        }

        ArrayList<ArrayList<String>> lineAndPrice = new ArrayList<>();
        lineAndPrice.add(lineItems);
        lineAndPrice.add(linePrices);

        System.out.println(lineItems.size());
        System.out.println(linePrices.size());
        return lineAndPrice;





    }
    boolean isParseable(int lineItems, int priceItems)
    {
        return lineItems == priceItems;
    }


}
