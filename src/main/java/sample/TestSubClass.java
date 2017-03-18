package sample;

import org.springframework.stereotype.Component;

@Component
public class TestSubClass {

  public String public_field = "public_field";
  private String private_field = "private_field";

  private String sub_private_method() {
    return "subclass_private_method has " + private_field;
  }

  public String sub_public_method() {
    return "subclass_public_method has " + public_field;
  }

  public String sub_public_method_call_private_method() {
    return "subclass_public_method called " + sub_private_method();
  }
}
