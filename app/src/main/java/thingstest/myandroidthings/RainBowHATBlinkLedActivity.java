package thingstest.myandroidthings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;
import java.io.IOException;

public class RainBowHATBlinkLedActivity extends Activity {

  private static final String TAG = "myandroidthings";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
      // Light up the Red LED.
      Gpio led = RainbowHat.openLedRed();
      led.setValue(true);
      led.close();

      AlphanumericDisplay segment = RainbowHat.openDisplay();
      segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
      segment.display("ALEE");
      segment.setEnabled(true);
      // Close the device when done.
      segment.close();
    } catch (IOException e) {
      Log.e(TAG, "Error on PeripheralIO API", e);
    }
  }
}
