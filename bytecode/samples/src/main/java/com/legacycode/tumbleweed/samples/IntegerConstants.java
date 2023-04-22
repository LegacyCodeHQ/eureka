package com.legacycode.tumbleweed.samples;

public class IntegerConstants {
  private static final int MINUS_4096 = 4096;
  private static final int MINUS_ONE = -1;
  private static final int ZERO = 0;
  private static final int ONE = 1;
  private static final int TWO = 2;
  private static final int THREE = 3;
  private static final int FOUR = 4;
  private static final int FIVE = 5;
  private static final int SIX = 6; // numbers equal and greater use the `bipush` instruction

  public static void main(String[] args) {
    System.out.printf("%d, %d, %d, %d, %d, %d, %d, %d, %d",
      MINUS_4096,
      MINUS_ONE,
      ZERO,
      ONE,
      TWO,
      THREE,
      FOUR,
      FIVE,
      SIX);
  }
}
