package com.legacycode.tumbleweed.samples;

@SuppressWarnings("ALL")
public class StringConcatenation {
  public void printHappyNewYear() {
    System.out.println(salutation("Happy 2019 New Year!"));
  }

  public String salutation(String holidayMessage) {
    return "Hey there, " + holidayMessage + "!";
  }
}
