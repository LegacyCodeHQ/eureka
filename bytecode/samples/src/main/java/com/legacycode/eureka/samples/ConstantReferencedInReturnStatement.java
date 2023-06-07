package com.legacycode.eureka.samples;

public class ConstantReferencedInReturnStatement {
  public static final String CONSTANT = "change is the only constant";

  public static String getConstant() {
    return CONSTANT;
  }

  public static void main(String[] args) {
    System.out.println(getConstant());
  }
}
