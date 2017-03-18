package sample;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RealTest {
  @Autowired
  private TestTargetClass instance;

  @Test
  public void publicメソッドをモックできる() {
    assertThat(instance.public_method(), is("public_method called public_sub"));

    TestTargetClass mockInstance = mock(TestTargetClass.class);
    when(mockInstance.public_method()).thenReturn("mocked_public_method");
    assertThat(mockInstance.public_method(), is("mocked_public_method"));
  }

  @Test
  public void privateメソッドを呼び出してテストできる() throws Exception {
    Method method = TestTargetClass.class.getDeclaredMethod("private_method");
    method.setAccessible(true);

    assertThat((String) method.invoke(instance), is("private_method called private_sub"));
  }

  @Test
  public void publicメソッドが呼ぶSubメソッドはSpyでモックできる() {
    TestTargetClass mockInstance = spy(new TestTargetClass());
    when(mockInstance.public_sub()).thenReturn("mocked_public_sub");
    assertThat(mockInstance.public_method(), is("public_method called mocked_public_sub"));
  }
}
