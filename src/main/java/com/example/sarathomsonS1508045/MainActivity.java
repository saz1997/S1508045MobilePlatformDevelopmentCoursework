package com.example.sarathomsonS1508045;

//
// Name                 __Sara Thomson_______
// Student ID           __S1508045___________
// Programme of Study   __Computing BSc(Hons)
//

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

//imports sarathomsonmpdcoursework.R;

public class MainActivity extends AppCompatActivity
{
    //private TextView rawDataDisplay;
    private Button startButton;
    private String result;
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private View mainView;

    EarthquakeAdapter adapter;

    InputStream urlSourceInputStream = new ByteArrayInputStream(urlSource.getBytes(StandardCharsets.UTF_8));
    private ListView quakelistView;
    LinkedList<Earthquake> allQuakes = new LinkedList<>();
    LinkedList<Earthquake> filtered = new LinkedList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final LinearLayout startLayout = findViewById(R.id.firstDate);
        final LinearLayout endLayout = findViewById(R.id.secondDate);


        final DatePicker datePicker = findViewById(R.id.Datepick);
        final DatePicker datePicker1 = findViewById(R.id.Datepick1);
        startLayout.setVisibility(View.GONE);
        endLayout.setVisibility(View.GONE);
        final Button button = (Button) findViewById(R.id.set);



        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                //datePicker.setVisibility(View.VISIBLE);
                startLayout.setVisibility(View.VISIBLE);
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {



                    @Override
                    public void onDateChanged(DatePicker datePicker, final int year, final int month, final int dayOfMonth) {
                        Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                        startLayout.setVisibility(View.GONE);
                        endLayout.setVisibility(View.VISIBLE);

                        final Date dateStart = new Date();

                        dateStart.setYear(year-1900);
                        dateStart.setMonth(month);
                        dateStart.setDate(dayOfMonth);
                        dateStart.setHours(0);
                        dateStart.setMinutes(0);
                        dateStart.setSeconds(0);

                        datePicker1.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {



                            @Override
                            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                Log.d("Date", "Year=" + year + " Month="
                                        + (month + 1) + " day=" + dayOfMonth);
                                startLayout.setVisibility(View.GONE);
                                endLayout.setVisibility(View.GONE);


                                Date dateEnd = new Date();

                                dateEnd.setYear(year-1900);
                                dateEnd.setMonth(month);
                                dateEnd.setDate(dayOfMonth);
                                dateEnd.setHours(23);
                                dateEnd.setMinutes(59);
                                dateEnd.setSeconds(59);


                                DateFormat dateFormat = new SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss");
                                Log.e("String", dateEnd.toString());

                                //loop

                                for(Earthquake currentEarthquake: allQuakes){
                                    final Earthquake eq = currentEarthquake;

                                    Date date = null;
                                    try {
                                        date = dateFormat.parse(currentEarthquake.getPubDate());
                                        System.out.println(date.toString());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    if(date.after(dateStart) && date.before(dateEnd)){

                                        filtered.add(eq);

                                    }

                                }

                                System.out.println(filtered.size());
                                if (filtered.size() != 0) {
                                    findValue(filtered);
                                    adapter = new EarthquakeAdapter(MainActivity.this, filtered);
                                    quakelistView.setAdapter(adapter);
                                }

                            }


                        });
                    }


                });
            }



        });







        mainView = findViewById(R.id.mainView);
        mainView.setBackgroundColor(getResources().getColor(R.color.white,null));

        // Set up the raw links to the graphical components
        //rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        //startButton = (Button)findViewById(R.id.startButton);
        //startButton.setOnClickListener(this);

        startProgress();

        // More Code goes here
    }

    // public void onClick(View aview)
    //{
    //  startProgress();


    Timer timer = new Timer();
    TimerTask update = new TimerTask(){
        @Override
        public void run (){
            startProgress();
        }
    };


    //}

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);
                    url1 += inputLine;

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception");
            }

            //
            // Now that you have the xml data you can parse it
            PullParser parser = new PullParser();
            allQuakes = parser.parse(url1);
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                    try{

                    findValue(allQuakes);
                    adapter = new EarthquakeAdapter(getApplicationContext(), allQuakes);


                    quakelistView = (ListView) findViewById(R.id.quakeList);


                    // start creation of second screen view

                    quakelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, SecondScreenActivity.class);
                            System.out.println(quakelistView.getItemAtPosition(position).toString());
                            intent.putExtra("Earthquake", quakelistView.getItemAtPosition(position).toString());
                            startActivity(intent);
                        }
                    });


                    quakelistView.setAdapter(adapter); } catch (NoSuchElementException e){
                        Log.e("Error Tag", "No Mobile Data Connection");
                    }
                    //rawDataDisplay.setText(result);
                }
            });
        }


    }


    // set filter


    public void findValue(LinkedList<Earthquake> list){

        if(list.getFirst() != null) {
            double north = list.getFirst().getLatlong()[0];
            int northi = 0;
            double south = list.getFirst().getLatlong()[0];
            int southi = 0;
            double east = list.getFirst().getLatlong()[0];
            int easti = 0;
            double west = list.getFirst().getLatlong()[0];
            int westi = 0;
            double lMagnitude = Double.parseDouble(list.getFirst().getMagnitude());
            int lMagi = 0;
            double shallowest = Double.parseDouble(list.getFirst().getDepth().replace(" km",""));
            int shi = 0;
            double deepest = Double.parseDouble(list.getFirst().getDepth().replace(" km",""));
            int dei = 0;
            int i = 0;

            //for loop

            for (Earthquake currentEarthquake : list) {
                //System.out.println(currentEarthquake.toString());
                if (currentEarthquake.getLatlong()[0] >= north) {
                    northi = i;
                    north = currentEarthquake.getLatlong()[0];
                } else if (currentEarthquake.getLatlong()[0] <= south) {
                    southi = i;
                    south = currentEarthquake.getLatlong()[0];
                }
                if (currentEarthquake.getLatlong()[1] >= east) {
                    easti = i;
                    east = currentEarthquake.getLatlong()[1];
                } else if (currentEarthquake.getLatlong()[1] <= west) {
                    westi = i;
                    west = currentEarthquake.getLatlong()[1];
                }
                if (Double.parseDouble(currentEarthquake.getMagnitude()) >= lMagnitude) {
                    lMagi = i;
                    lMagnitude = Double.parseDouble(currentEarthquake.getMagnitude());
                }
                if (Double.parseDouble(currentEarthquake.getDepth().replace(" km","")) >= shallowest) {
                    shi = i;
                    shallowest = Double.parseDouble(currentEarthquake.getDepth().replace(" km",""));
                } else if (Double.parseDouble(currentEarthquake.getDepth().replace(" km","")) >= deepest) {
                    dei = i;
                    deepest = Double.parseDouble(currentEarthquake.getDepth().replace(" km",""));
                }
                i++;
            }

                    //print data

                    System.out.println("");
                    System.out.println(i);
                    list.get(northi).setCategory("Northern");
                    System.out.println(northi);
                    list.get(southi).setCategory("Southern");
                    System.out.println(southi);
                    list.get(westi).setCategory("Western");
                    System.out.println(westi);
                    list.get(easti).setCategory("Eastern");
                    System.out.println(easti);
                    list.get(lMagi).setCategory("Largest Magnitude");
                    System.out.println(lMagi);
                    list.get(shi).setCategory("Shallowest");
                    System.out.println(shi);
                    list.get(dei).setCategory("Deepest");
                    System.out.println(dei);


                }
            }

        }


