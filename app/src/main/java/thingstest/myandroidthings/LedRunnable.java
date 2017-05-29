package thingstest.myandroidthings;

import android.os.Handler;
import android.util.Log;
import com.google.android.things.pio.Gpio;
import java.io.IOException;

public class LedRunnable implements Runnable {

  private static final String TAG = "LedRunnable";
  private Gpio mLedGpio;
  private static final int INTERVAL_BETWEEN_BLINKS_MS = 1000;

  public LedRunnable(Gpio mLedGpio) {
    this.mLedGpio = mLedGpio;
  }

  @Override
  public void run() {
    Handler mHandler = new Handler();

    // Exit if the GPIO is already closed
    if (mLedGpio == null) {
      return;
    }

    try {
      // Step 3. Toggle the LED state
      mLedGpio.setValue(!mLedGpio.getValue());

      // Step 4. Schedule another event after delay.
      mHandler.postDelayed(this, INTERVAL_BETWEEN_BLINKS_MS);
    } catch (IOException e) {
      Log.e(TAG, "Error on PeripheralIO API", e);
    }
  }
}

