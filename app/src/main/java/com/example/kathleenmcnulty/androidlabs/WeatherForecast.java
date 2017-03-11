package com.example.kathleenmcnulty.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        try{
            ForecastQuery thread =
                    new ForecastQuery("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
            thread.execute();
        }catch(Exception e){
            Log.d("errorforecast", e.toString());
        }

    }
    private class ForecastQuery extends AsyncTask<String, Integer, String[]>
    {
        String min;
        String max;

        String current;
        Bitmap image;
        ProgressBar loadingimage;
        ImageView imageView;

        URL url, imageURL;
        String iconVal;
        String in = "in";
        public ForecastQuery(String url){
            loadingimage = (ProgressBar) findViewById(R.id.progressBar);
            loadingimage.setMax(100);
            imageView = (ImageView) findViewById(R.id.imageView3);
            try{
                this.url = new URL(url);
            }catch(MalformedURLException e){
                e.printStackTrace();
            }
        }
        @Override
        protected String[] doInBackground(String ... args)
        {
            String[] entries = new String[4];
           try {
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();



               parser.require(XmlPullParser.START_TAG, null, "current");
                //Log.d("parserfeed", parser.getAttributeValue(null, "current"));
               while (parser.next() != XmlPullParser.END_DOCUMENT) {
                     if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    String name = parser.getName();


                    // Starts by looking for the entry tag
                    if (name.equals("temperature")) {
                        //entries.add(String.valueOf(parser));
                        //Log.d("min", min);
                        min=parser.getAttributeValue( null, "min");
                        entries[0] = min;
                        publishProgress(25);
                        try{
                            Thread.sleep(1000);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        max=parser.getAttributeValue( null, "max");
                        entries[1] = max;
                        publishProgress(50);
                        try{
                            Thread.sleep(1000);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        current=parser.getAttributeValue( null, "value");
                        entries[2] = current;
                        publishProgress(75);
                        try{
                            Thread.sleep(1000);
                        }catch(Exception e){
                            e.printStackTrace();
                        }



                    } else if(name.equals("weather")){
                        //skip(parser);
                        iconVal = parser.getAttributeValue(null, "icon");
                        entries[3] = iconVal;

                        imageURL = new URL("http://openweathermap.org/img/w/" + iconVal + ".png");
                        if(fileExistance(iconVal+".png")){
                            FileInputStream fis = null;
                            try {
                                ;
                                fis = new FileInputStream(getBaseContext().getFileStreamPath(iconVal+".png"));

                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            image = BitmapFactory.decodeStream(fis);

                        }else{

                            HttpUtils object=new HttpUtils();
                            image  = object.getImage(imageURL);
                            FileOutputStream outputStream = openFileOutput( iconVal + ".png", Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        }

                        publishProgress(100);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return entries;
        }
        @Override
        protected void onProgressUpdate(Integer... prog){



            loadingimage.setProgress(prog[0]);
            loadingimage.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPostExecute(String[] res){

            super.onPostExecute(res);
            TextView txt = (TextView) findViewById(R.id.textView5);
            TextView txt1 = (TextView) findViewById(R.id.textView6);
            TextView txt2 = (TextView) findViewById(R.id.textView7);
            txt.setText(" current "+res[2]);
            txt1.setText(" min "+res[0]);
            txt2.setText("max "+res[1]);
            loadingimage.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(image);
            //res[0] : min
            //res[1] : max
            //res[2] : currentTemp
            //res[3] : value

            /*outputStream.close();*/
            //return"";
        }

    }
    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    private class HttpUtils {
        public  Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public  Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

    }
}

