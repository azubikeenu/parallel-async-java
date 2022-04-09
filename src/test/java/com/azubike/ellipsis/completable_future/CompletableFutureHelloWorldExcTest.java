package com.azubike.ellipsis.completable_future;

import com.azubike.ellipsis.services.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExcTest {
  @Mock HelloWorldService hws = mock(HelloWorldService.class);
  @InjectMocks CompletableFutureHelloWorldExc cfhwc;

  @Test
  void helloWorld_async_approach_2() {
    when(hws.hello()).thenThrow(new RuntimeException("An Exception occurred"));
    when(hws.world()).thenCallRealMethod();
    final String s = cfhwc.helloWorld_async_approach_handle();
    assertEquals(" WORLD! HI", s);
  }

  @Test
  void helloWorld_async_approach_3() {
    when(hws.hello()).thenThrow(new RuntimeException("An Exception occurred"));
    when(hws.world()).thenThrow(new RuntimeException("An Exception occurred"));
    final String s = cfhwc.helloWorld_async_approach_handle();
    assertEquals(" HI", s);
  }

  @Test
  void helloWorld_async_approach_4() {
    when(hws.hello()).thenCallRealMethod();
    when(hws.world()).thenCallRealMethod();
    final String s = cfhwc.helloWorld_async_approach_handle();
    assertEquals("HELLO WORLD! HI", s);
  }

  @Test
  void helloWorld_async_approach_5() {
    when(hws.hello()).thenCallRealMethod();
    when(hws.world()).thenCallRealMethod();
    final String s = cfhwc.helloWorld_async_approach_exceptionally();
    assertEquals("HELLO WORLD! HI", s);
  }

  @Test
  void helloWorld_async_approach_6() {
    when(hws.hello()).thenCallRealMethod();
    when(hws.world()).thenThrow(new RuntimeException("Exception occurred"));
    final String s = cfhwc.helloWorld_async_approach_exceptionally();
    assertEquals(" HI", s);
  }

  @Test
  void helloWorld_async_approach_7() {
    when(hws.hello()).thenThrow(new RuntimeException("Exception Occurred"));
    when(hws.world()).thenThrow(new RuntimeException("Exception occurred"));
    final String s = cfhwc.helloWorld_async_approach_exceptionally();
    assertEquals(" HI", s);
  }
  @Test
  void helloWorld_async_approach_8() {
    when(hws.hello()).thenThrow(new RuntimeException("Exception Occurred"));
    when(hws.world()).thenThrow(new RuntimeException("Exception occurred"));
    final String s = cfhwc.helloWorld_async_approach_when_complete();
    assertEquals(" HI", s);
  }
  @Test
  void helloWorld_async_approach_9() {
    when(hws.hello()).thenCallRealMethod();
    when(hws.world()).thenCallRealMethod();
    final String s = cfhwc.helloWorld_async_approach_when_complete();
    assertEquals("HELLO WORLD! HI", s);
  }
}
