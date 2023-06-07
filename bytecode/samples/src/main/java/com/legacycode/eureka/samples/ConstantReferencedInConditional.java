package com.legacycode.eureka.samples;

public class ConstantReferencedInConditional extends Activity {
  private static final int TAKE_PHOTO = 1;

  @Override
  public void onActivityResult(int requestCode, int resultCode) {
    super.onActivityResult(requestCode, resultCode);
    if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
      System.out.println("Nice picture!");
    }
  }
}

class Activity {
  protected static final int RESULT_OK = 100;

  public void onActivityResult(int requestCode, int resultCode) {
    // no-op
  }
}
