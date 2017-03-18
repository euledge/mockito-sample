package sample;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
// PowerMock対象のクラス
@PrepareForTest({TestTargetClass.class})
public class PowerMockTest {
  // @InjectMocks
  // private TestTargetClass instance;

  @Test
  public void privateメソッドはPowerMockitoでモックできる() throws Exception {
    TestTargetClass mockInstance = PowerMockito.mock(TestTargetClass.class);
    PowerMockito.when(mockInstance, MemberMatcher.method(TestTargetClass.class, "private_method"))
        .withNoArguments().thenReturn("mocked_private_method");

    Method method = TestTargetClass.class.getDeclaredMethod("private_method");
    method.setAccessible(true);

    assertThat((String) method.invoke(mockInstance), is("mocked_private_method"));
  }

  @Test
  public void publicメソッドが呼ぶprivateのSubメソッドをモックできる() throws Exception {
    TestTargetClass mockInstance = PowerMockito.spy(new TestTargetClass());
    PowerMockito.when(mockInstance, MemberMatcher.method(TestTargetClass.class, "private_sub"))
        .withNoArguments().thenReturn("mocked_private_sub");

    assertThat(mockInstance.public_method_call_private_method(),
        is("public_method called mocked_private_sub"));
  }

  @Test
  public void staticメソッドをモックできる() {
    PowerMockito.mockStatic(TestTargetClass.class);
    PowerMockito.when(TestTargetClass.static_method()).thenReturn("mocked_static_method");
    assertThat(TestTargetClass.static_method(), is("mocked_static_method"));
  }

}
