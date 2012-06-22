package com.saucelabs.ondemand;

import java.util.HashMap;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.misc.BASE64Encoder;
import java.lang.InterruptedException;

public class DownloadArtifacts {
    private static URLConnection connection;
    
    public static void DownloadVideo(HashMap jobInfo, String location) throws MalformedURLException, IOException, InterruptedException {
        String uri = "https://saucelabs.com/rest/" + jobInfo.get("username") + "/jobs/" + jobInfo.get("sessionId") + "/results/video.flv";
        Download(uri, jobInfo, location);
    }
    
    public static void DownloadLog(HashMap jobInfo, String location) throws MalformedURLException, IOException, InterruptedException {
        String uri = "https://saucelabs.com/rest/" + jobInfo.get("username") + "/jobs/" + jobInfo.get("sessionId") + "/results/selenium-server.log";
        Download(uri, jobInfo, location);
    }
    
    private static void Download(String uri, HashMap jobInfo, String location) throws MalformedURLException, IOException, InterruptedException  {
        // most people will want the video more than the log
        String suffix = ".flv";

        // but they could be debugging and need the log
        if (uri.endsWith(".log")) {
            suffix = ".log";
        }
        
        URL url = new URL(uri);

        String userPassword = jobInfo.get("username") + ":" + jobInfo.get("key");
        String encoding = new BASE64Encoder().encode(userPassword.getBytes());

        int code = 404;
        while (code == 404) {
          connection = url.openConnection();
          connection.setRequestProperty ("Authorization", "Basic " + encoding);
          connection.connect();

          if (connection instanceof HttpURLConnection) {
              HttpURLConnection httpConnection = (HttpURLConnection) connection;
              code = httpConnection.getResponseCode();
              if (code == 404) {
                  Thread.sleep(10000);
              }
          }
        }

        InputStream stream = connection.getInputStream();
        BufferedInputStream in = new BufferedInputStream(stream);
        String saveName = jobInfo.get("methodName") + "-" + jobInfo.get("os") + "-" + jobInfo.get("browser") + "-" + jobInfo.get("browserVersion") + suffix;
        FileOutputStream file = new FileOutputStream(location + System.getProperty("file.separator") + saveName);
        BufferedOutputStream out = new BufferedOutputStream(file);
        int i;
        while ((i = in.read()) != -1) {
        out.write(i);
        }
        out.flush();
    }
}