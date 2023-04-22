package com.legacycode.tumbleweed.samples;

@SuppressWarnings("ALL")
public class LambdaAccessingField {
  private int count = 0;

  public void increment() {
    ((Action) () -> count++).doIt();
  }

  interface Action {
    void doIt();
  }
}
