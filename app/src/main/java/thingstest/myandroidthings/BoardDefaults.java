package thingstest.myandroidthings;

import android.os.Build;
import com.google.android.things.pio.PeripheralManagerService;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class BoardDefaults {
  private static final String DEVICE_EDISON_ARDUINO = "edison_arduino";
  private static final String DEVICE_EDISON = "edison";
  private static final String DEVICE_JOULE = "joule";
  private static final String DEVICE_RPI3 = "rpi3";
  private static final String DEVICE_IMX6UL_PICO = "imx6ul_pico";
  private static final String DEVICE_IMX6UL_VVDN = "imx6ul_iopb";
  private static final String DEVICE_IMX7D_PICO = "imx7d_pico";
  private static String sBoardVariant = "";

  /**
   * Return the GPIO pin that the LED is connected on.
   * For example, on Intel Edison Arduino breakout, pin "IO13" is connected to an onboard LED
   * that turns on when the GPIO pin is HIGH, and off when low.
   */
  public static String getGPIOForLED() {
    switch (getBoardVariant()) {
      case DEVICE_EDISON_ARDUINO:
        return "IO13";
      case DEVICE_EDISON:
        return "GP45";
      case DEVICE_JOULE:
        return "J6_25";
      case DEVICE_RPI3:
        return "BCM6";
      case DEVICE_IMX6UL_PICO:
        return "GPIO4_IO20";
      case DEVICE_IMX6UL_VVDN:
        return "GPIO3_IO06";
      case DEVICE_IMX7D_PICO:
        return "GPIO_34";
      default:
        throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
    }
  }

  public static String getGPIOForRedLED() {
    switch (getBoardVariant()) {
      case DEVICE_IMX7D_PICO:
        return "GPIO_34";
      default:
        throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
    }
  }

  public static String getGPIOForBlueLED() {
    switch (getBoardVariant()) {
      case DEVICE_IMX7D_PICO:
        return "GPIO_37";
      default:
        throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
    }
  }

  public static String getGPIOForGreenLED() {
    switch (getBoardVariant()) {
      case DEVICE_IMX7D_PICO:
        return "GPIO_32";
      default:
        throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
    }
  }

  private static String getBoardVariant() {
    if (!sBoardVariant.isEmpty()) {
      return sBoardVariant;
    }
    sBoardVariant = Build.DEVICE;
    // For the edison check the pin prefix
    // to always return Edison Breakout pin name when applicable.
    if (sBoardVariant.equals(DEVICE_EDISON)) {
      PeripheralManagerService pioService = new PeripheralManagerService();
      List<String> gpioList = pioService.getGpioList();
      if (gpioList.size() != 0) {
        String pin = gpioList.get(0);
        if (pin.startsWith("IO")) {
          sBoardVariant = DEVICE_EDISON_ARDUINO;
        }
      }
    }
    return sBoardVariant;
  }
}
