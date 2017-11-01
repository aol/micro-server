package com.oath.micro.server.slack;

import javax.net.ssl.HttpsURLConnection;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SlackMessageSender {
    
    private SlackConfiguration slackConfiguration;
    
    @Autowired
    public SlackMessageSender(SlackConfiguration slackConfiguration){

        this.slackConfiguration = slackConfiguration;
    }

    public int postMessageToSlack(String msg){

        try {
            URL obj;
            obj = new URL(slackConfiguration.getWebhookUri());
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "com.oath.micro.server");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    
            String urlParameters = "{\"text\": \"" + msg + "\"}";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr;
            wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
    
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            return responseCode;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
