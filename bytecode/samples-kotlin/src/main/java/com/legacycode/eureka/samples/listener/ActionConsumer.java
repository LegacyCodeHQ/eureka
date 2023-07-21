package com.legacycode.eureka.samples.listener;

public class ActionConsumer {
  public void waitForAction(ActionListener listener) {
    listener.onAction();
  }
}
