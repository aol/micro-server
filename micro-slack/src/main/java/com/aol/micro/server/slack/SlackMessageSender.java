package com.aol.micro.server.slack;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SlackMessageSender {
    
    @Autowired
    private SlackConfiguration slackConfiguration;
    
    public SlackMessageSender(@Value("${slack.webhookUri}") String webhookUrl){
        this.slackConfiguration = new SlackConfiguration(webhookUrl);
    }

    @GET
    @Path("/slack/message")
    public String slackMessageViaGet(@QueryParam("txt") final String msg) {
        postMessageToSlack(msg);
        return "OK";
    }
    
    //https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
    private int postMessageToSlack(String msg){

        try {
            URL obj;
            obj = new URL(slackConfiguration.getWebhookUri());
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
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
