import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ListenGpioExample {

	private static long lastRegisteredButtonClick = System.currentTimeMillis();
	private static long multiPressTimeout = 100;
	
    public static void main(String args[]) throws InterruptedException {
        final GpioController gpio = GpioFactory.getInstance();

        // Green
        final GpioPinDigitalInput myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
        // Red
        final GpioPinDigitalInput myButton3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);
        // Yellow
        final GpioPinDigitalInput myButton7 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_DOWN);
        // Blue
        final GpioPinDigitalInput myButton0 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);

        Map<Integer, String> pinColorMapping = new HashMap<>();
        pinColorMapping.put(2, "GREEN");
        pinColorMapping.put(3, "RED");
        pinColorMapping.put(7, "YELLOW");
        pinColorMapping.put(0, "BLUE");
        
        // set shutdown state for this input pin
        myButton2.setShutdownOptions(true);
        myButton3.setShutdownOptions(true);
        myButton7.setShutdownOptions(true);
        myButton0.setShutdownOptions(true);

        GpioPinListenerDigital listener = event -> {
        	if (event.getState() == PinState.HIGH && lastRegisteredButtonClick < System.currentTimeMillis() - multiPressTimeout) {
        		lastRegisteredButtonClick = System.currentTimeMillis();
        		// Name = 'GPIO xx'
        		int pinNumber = Integer.parseInt(event.getPin().getName().split(" ")[1]);
        		
        		String color = pinColorMapping.get(pinNumber);
        		try {
        			System.out.println("About to post a button press for color: " + color);
        			Process process = Runtime.getRuntime().exec(new String[] {"curl", "-X", "POST", "http://192.168.4.100:8080/spiques/pressed/" + color, ">>", "curl.log"});
        			process.waitFor();
        			System.out.println("exit code of curl: " + process.exitValue());
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        };
        
        // create and register gpio pin listener
        myButton2.addListener(listener);
        myButton3.addListener(listener);
        myButton7.addListener(listener);
        myButton0.addListener(listener);

        System.out.println("Controls up and running...");

        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(500);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        //gpio.shutdown();
    }
}