package com.legacycode.tumbleweed.samples.listener;

public class ActionConsumer {
  public void waitForAction(ActionListener listener) {
    listener.onAction();
  }
}
