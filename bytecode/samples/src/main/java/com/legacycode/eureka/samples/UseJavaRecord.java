package com.legacycode.eureka.samples;

import java.util.concurrent.TimeUnit;

public class UseJavaRecord {
  private final Video video = new Video("Installing Eureka", TimeUnit.MINUTES.toMillis(2));

  public void printVideoTitle() {
    System.out.println(video.title());
  }

  public void printVideoDuration() {
    System.out.println(video.duration());
  }

  @SuppressWarnings("unused")
  public void printVideoInformation() {
    printVideoTitle();
    printVideoDuration();
  }
}
