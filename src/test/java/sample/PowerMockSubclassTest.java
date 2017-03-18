package sample;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(PowerMockRunner.class)
// PowerMock対象のクラス
@PrepareForTest({TestSubClass.class})
@SpringBootTest
public class PowerMockSubclassTest {
  @InjectMocks
  private TestTargetClass instance;
  private TestSubClass testSubClass = PowerMockito.spy(new TestSubClass());


  @Test
  public void サブクラスのpublicメソッドをモックできる() {
    PowerMockito.when(testSubClass.sub_public_method()).thenReturn("mocked_subclass_public_method");
    assertThat(instance.public_subclass_public_method(),
        is("public_subclass_method called mocked_subclass_public_method"));
  }

  @Test
  public void サブクラスのpublicフィールドをモックできる() {
    Whitebox.setInternalState(testSubClass, "public_field", "mocked_public_field");
    assertThat(instance.public_subclass_public_method(),
        is("public_subclass_method called subclass_public_method has mocked_public_field"));
  }

  @Test
  public void サブクラスのprivateメソッドをモックできる() throws Exception {
    PowerMockito.when(testSubClass, MemberMatcher.method(TestSubClass.class, "sub_private_method"))
        .withNoArguments().thenReturn("mocked_sub_private_method");

    assertThat(instance.public_subclass_private_method(), is(
        "public_subclass_method called subclass_public_method called mocked_sub_private_method"));
  }

  @Test
  public void サブクラスのprivateフィールドをモックできる() {
    Whitebox.setInternalState(testSubClass, "private_field", "mocked_private_field");
    assertThat(instance.public_subclass_private_method(), is(
        "public_subclass_method called subclass_public_method called subclass_private_method has mocked_private_field"));
  }
}
