package io.redgreen.tumbleweed.samples;

public class StaticBlock {
  private static final String HELLO_WORLD = "Hello, world!";

  static {
    sayHello();
  }

  public static void sayHello() {
    System.out.println(HELLO_WORLD);
  }
}
