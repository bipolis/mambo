package org.org.bipolis.mambo.jaxrs.ext.media.xml.itest;

import java.util.List;

public class ExampleDTO {

  private String field;

  private List<String> list;

  public ExampleDTO(String field, List<String> list) {
    this.field = field;
    this.list = list;
  }

  public List<String> getList() {
    return list;
  }

  public void setList(List<String> list) {
    this.list = list;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }
}
