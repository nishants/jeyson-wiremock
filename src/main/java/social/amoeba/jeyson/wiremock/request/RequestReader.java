package social.amoeba.jeyson.wiremock.request;

import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestReader {
  public static Map queryParams(String url){
    HashMap params = new HashMap<>();
    if(url.contains("?")){
      String[] pairs = url.split("\\?")[1].split("&");
      for(int i = 0 ; i < pairs.length; i++){
        String tokens[] = pairs[i].split("="),
            name = tokens[0],
            value = tokens.length == 2 ? tokens[1] : "";

        params.put(name, value);
      }
    }
    return params;
  }
  public static Map read(Request request) throws IOException {
    String mimeType = request.getHeaders().getContentTypeHeader().mimeTypePart();
    boolean isJSON = mimeType.equalsIgnoreCase("application/json"),
            isXML  = mimeType.equalsIgnoreCase("application/xml") || mimeType.equalsIgnoreCase("text/xml");

    byte[] requestBody = request.getBody();

    Map body = isJSON ? JSON.parse(requestBody) : isXML ? XML.parse(requestBody) : null,
        headers = new HashMap<>(),
        result  = isXML ? new XMLMap() : new HashMap<>(),
        cookies = new HashMap<>();

    Iterator<HttpHeader> iterator = request.getHeaders().all().iterator();
    while(iterator.hasNext()){
      HttpHeader next = iterator.next();
      headers.put(next.key(), next.values());
    }
    if(isXML){
      return new XMLRequest(request);
    }


    return new JSONRequest(request);
  }

}
