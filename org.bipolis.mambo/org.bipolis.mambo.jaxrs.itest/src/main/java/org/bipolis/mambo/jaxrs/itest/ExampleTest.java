package org.bipolis.mambo.jaxrs.itest;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.bipolis.mambo.jaxrs.itest.example.basic.application.BasicApplication;
import org.bipolis.mambo.jaxrs.itest.example.basic.application.RessourceInBasicApp;
import org.junit.Test;


public class ExampleTest {

  // private final BundleContext context = FrameworkUtil.getBundle(ExampleTest.class)
  // .getBundleContext();

  private String getResult(String appPath,
                           String ressourcePath,
                           String methodPath)
          throws Throwable {
    StringBuilder sb = new StringBuilder();
    sb.append(appPath == null ? "" : "/" + appPath);
    sb.append(ressourcePath == null ? "" : "/" + ressourcePath);
    sb.append(methodPath == null ? "" : "/" + methodPath);

    URLConnection connection = new URL("http://localhost:8080" + sb.toString()).openConnection();
    HttpURLConnection con = (HttpURLConnection) connection;

    con.setRequestMethod("GET");
    // for filterTests
    con.setRequestProperty("User-Agent", "Mozilla/5.0");

    int responseCode = con.getResponseCode();
    if (responseCode != 200) {
      throw new Exception("responsecode " + responseCode);
    }

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    return response.toString();

  }

  @Test
  public void testExample()
          throws Throwable {

    Thread.sleep(5000);
    assertEquals(RessourceInBasicApp.class.getName(), getResult(BasicApplication.APPLICATION_NAME,
            RessourceInBasicApp.RESSOURCE_NAME, "value"));
    // assertEquals(RessourceInBasicAppA.class.getName(),
    // getResult(BasicApplicationA.APPLICATION_NAME,
    // RessourceInBasicAppA.RESSOURCE_NAME, "value"));

  }

}
