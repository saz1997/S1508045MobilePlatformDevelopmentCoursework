package com.example.sarathomsonS1508045;

//
// Name                 __Sara Thomson_______
// Student ID           __S1508045___________
// Programme of Study   __Computing BSc(Hons)
//


import java.io.StringReader;
import java.util.LinkedList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;


public class PullParser {

    LinkedList <Earthquake> allQuakes;
    private Earthquake earthquake;
    private String text;



    public PullParser(){
        allQuakes = new LinkedList<>();

    }



    //get all earthquakes

    public LinkedList<Earthquake> parse(String pDataToParse) {

        Boolean quakeStart = false;
        XmlPullParserFactory factory;
        XmlPullParser pparser;





        try{

            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            pparser = factory.newPullParser();
            pparser.setInput(new StringReader(pDataToParse));

            int eventType = pparser.getEventType();

            while(eventType !=XmlPullParser.END_DOCUMENT) {
                String tagname = pparser.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("item")){
                            earthquake = new Earthquake();
                        }
                        break;
                    case XmlPullParser.TEXT:

                        text = pparser.getText();
                        break;



                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("image")) {
                            quakeStart = true;
                        } else
                        if (tagname.equalsIgnoreCase("item")) {
                            allQuakes.add(earthquake);
                        } else if(tagname.equalsIgnoreCase("title")  && quakeStart == true ) {
                            earthquake.setTitle(text);
                        } else if(tagname.equalsIgnoreCase("description") && quakeStart == true ) {

                            //parseDescription(text);
                            String temp = text;
                            String[] array = temp.split(";");


                            //location
                            earthquake.setLocation(array[1].substring(11));


                            //latlong
                            earthquake.setLatLong(array[2].substring(11));


                            //depth
                            earthquake.setDepth(array[3].substring(7));

                            //Magnitude
                            earthquake.setMagnitude((array[4].substring(11)));



                            earthquake.setDescription(text);
                        } else if(tagname.equalsIgnoreCase("pubDate")) {
                            earthquake.setPubDate(text);
                        }


                    default:
                        break;
                }
                eventType = pparser.next();

            }


        } catch (XmlPullParserException ae1){

        } catch (IOException ae1) {

        }
        return allQuakes;



    }





}
