package tfip.moddb.swap.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class TelegramService {
    public boolean sendMsg(String chatid, String msg){
        RestTemplate template = new RestTemplate();
        String url = "https://telegram.arcanetrading.co/camper?chatid="+ chatid +"&msg="+msg;

        RequestEntity<Void> req = RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        JsonObject respMsg = (JsonObject) JsonParser.parseString(resp.getBody());
        if (respMsg.get("message").getAsString().equals("Sent Successfully") ){
            return true;
        }
        return false;
    }

}
