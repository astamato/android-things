package thingstest.myandroidthings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.things.contrib.driver.pwmspeaker.Speaker;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;
import java.io.IOException;

public class RainBowHATBlinkLedActivity extends Activity {

  private static final String TAG = "myandroidthings";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
      blinkLed();
    } catch (IOException e) {
      Log.e(TAG, "Error on PeripheralIO API", e);
    } catch (Exception e) {
    }
  }

  private void blinkLed() throws IOException {
    Gpio led = RainbowHat.openLedRed();
    led.setValue(true);
    led.close();
  }

  private void playSound() throws IOException, InterruptedException {
    Speaker buzzer = RainbowHat.openPiezo();
    buzzer.play(440);
    Thread.sleep(1500);
    // Stop the buzzer.
    buzzer.stop();
    // Close the device when done.
    buzzer.close();
  }
}
