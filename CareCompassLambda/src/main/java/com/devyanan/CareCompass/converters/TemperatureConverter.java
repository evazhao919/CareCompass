package com.devyanan.CareCompass.converters;

public class TemperatureConverter {
    /**
     * Converts Fahrenheit to Celsius.
     * @param fahrenheit The temperature in Fahrenheit.
     * @return The temperature converted to Celsius.
     */
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    /**
     * Converts Celsius to Fahrenheit.
     * @param celsius The temperature in Celsius.
     * @return The temperature converted to Fahrenheit.
     */
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    //Since everyone's usage habits are different, for a better user experience not sure will use it or not, keep it for now
    //For example, I'm personally used to using Celsius, but many people choose to use Fahrenheit
    //Consider this class for use if the conversion can be displayed on the page later or in red font when the body temperature is below or above normal
}
