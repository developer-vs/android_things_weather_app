# Weather App an application for the Android Things 

## Units format
Description: Standard, metric, and imperial units are available.

> Parameters:units metric, imperial. 

When you do not use units parameter, format is Standard by default.


### Temperature is available in Fahrenheit, Celsius and Kelvin units:

    • For temperature in Fahrenheit use "units=imperial"
    • For temperature in Celsius use "units=metric"
    • Temperature in "Kelvin is used by default", no need to use units parameter in API call


### In MainActivity.java add information abot your city:
    cityName = "London";
    countryName = "UK";
    temperatureUnit = "metric";

    // Only for test
    private String apiKey = "b6907d289e10d714a6e88b30761fae22";


#### Links

List of all API parameters with units (https://openweathermap.org/weather-data)

By default the temperature showing in Kelvin units, see documentation (https://openweathermap.org/current)

To work with your city required to get API key (https://home.openweathermap.org/)
