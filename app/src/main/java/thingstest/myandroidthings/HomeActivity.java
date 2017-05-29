package thingstest.myandroidthings;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import java.io.IOException;

public class HomeActivity extends Activity {

  private static final String TAG = "myandroidthings";
  private static final int INTERVAL_BETWEEN_BLINKS_MS = 1000;
  private static final String RED_LED_PIN_NAME = "GPIO_34";
  private static final String GREEN_LED_PIN_NAME = "GPIO_32";
  private static final String BLUE_LED_PIN_NAME = "GPIO_37";

  //Available gpios: GPIO_10, GPIO_128, GPIO_172, GPIO_173, GPIO_174, GPIO_175, GPIO_32, GPIO_33, GPIO_34, GPIO_35, GPIO_37,
  // GPIO_39

  private Handler mHandler = new Handler();

  private Gpio mLedGpio;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    PeripheralManagerService service = new PeripheralManagerService();
    Log.d(TAG, "Available GPIO: " + service.getGpioList());

    try {
      String pinName = BLUE_LED_PIN_NAME;
      mLedGpio = service.openGpio(pinName);
      mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
      Log.i(TAG, "Start blinking LED GPIO pin");
      // Post a Runnable that continuously switch the state of the GPIO, blinking the
      // corresponding LED
      mHandler.post(mBlinkRunnable);
    } catch (IOException e) {
      Log.e(TAG, "Error on PeripheralIO API", e);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Step 4. Remove handler events on close.
    mHandler.removeCallbacks(mBlinkRunnable);

    // Step 5. Close the resource.
    if (mLedGpio != null) {
      try {
        mLedGpio.close();
      } catch (IOException e) {
        Log.e(TAG, "Error on PeripheralIO API", e);
      }
    }
  }

  private Runnable mBlinkRunnable = new Runnable() {
    @Override
    public void run() {
      // Exit if the GPIO is already closed
      if (mLedGpio == null) {
        return;
      }

      try {
        // Step 3. Toggle the LED state
        mLedGpio.setValue(!mLedGpio.getValue());

        // Step 4. Schedule another event after delay.
        mHandler.postDelayed(mBlinkRunnable, INTERVAL_BETWEEN_BLINKS_MS);
      } catch (IOException e) {
        Log.e(TAG, "Error on PeripheralIO API", e);
      }
    }
  };
}
