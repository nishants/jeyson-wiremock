package social.amoeba.jeyson.wiremock;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import social.amoeba.jeyson.wiremock.request.ResponseBuilder;

import javax.script.ScriptException;
import java.io.IOException;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class JeysonWiremock extends ResponseDefinitionTransformer {

  @Override
  public ResponseDefinition transform(Request request,
                                      ResponseDefinition responseDefinition,
                                      FileSource files,
                                      Parameters parameters) {


    ResponseDefinitionBuilder builder = new ResponseDefinitionBuilder().like(responseDefinition);
    try {
      Map scope             = new HashMap<>();
      String templatesHome  = files.getPath(),
             relativePath   = responseDefinition.getBodyFileName();

      byte[] responseBody = ResponseBuilder.render(scope, templatesHome, relativePath);

      builder = builder.withBody(responseBody);
    } catch (IOException e) {
      System.err.println(e.getStackTrace());
    } catch (URISyntaxException e) {
      System.err.println(e.getStackTrace());
    } catch (NoSuchMethodException e) {
      System.err.println(e.getStackTrace());
    } catch (ScriptException e) {
      System.err.println(e.getStackTrace());
    }

    return builder.build();
  }


  public String getName() {
    return "Jeyson";
  }
}