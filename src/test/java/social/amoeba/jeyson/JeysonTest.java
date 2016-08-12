package social.amoeba.jeyson;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JeysonTest {

  private Jeyson jeyson;
  private String jeysonJs     = "/jeyson-contract.js";
  private String __files      = "/__files";
  private String __helloFile  = "/__files/hello/hello.json";
  private String helloFileContent;

  @Before
  public void setUp() throws Exception {
    jeyson = new Jeyson(Paths.get(this.getClass().getResource(__files).toURI()));
    helloFileContent = new String(Files.readAllBytes(Paths.get(this.getClass().getResource(__helloFile).toURI())));
  }

  @Test
  public void testGetTemplateCallBack() throws Exception {
    String result = jeyson.compile(null, "{'id': 1}");
    assertThat(result, is(helloFileContent));
  }
}