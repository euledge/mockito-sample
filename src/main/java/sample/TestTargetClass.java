package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestTargetClass {
  @Autowired
  private TestSubClass testSubClass;

  private String private_method() {
    return "private_method called " + private_sub();
  }

  private String private_sub() {
    return "private_sub";
  }

  public String public_method() {
    return "public_method called " + public_sub();
  }

  public String public_sub() {
    return "public_sub";
  }

  public String public_method_call_private_method() {
    return "public_method called " + private_sub();
  }

  public static String static_method() {
    return "static_method";
  }

  public String public_subclass_public_method() {
    return "public_subclass_method called " + testSubClass.sub_public_method();
  }

  public String public_subclass_private_method() {
    return "public_subclass_method called " + testSubClass.sub_public_method_call_private_method();
  }
}
