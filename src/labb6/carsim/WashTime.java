package labb6.carsim;

import labb6.ExponentialRandomStream;
import labb6.UniformRandomStream;

public class WashTime {

    private final ExponentialRandomStream arrivalStream;
    private final UniformRandomStream fastWashStream;
    private final UniformRandomStream slowWashStream;

    public WashTime(ExponentialRandomStream arrivalStream,
            UniformRandomStream fastWashStream,
            UniformRandomStream slowWashStream) {
        this.arrivalStream = arrivalStream;
        this.fastWashStream = fastWashStream;
        this.slowWashStream = slowWashStream;
    }

    double nextArrival(double currentTime) {
        return currentTime + arrivalStream.next();
    }

    double nextFast(double currentTime) {
        return currentTime + fastWashStream.next();
    }

    double nextSlow(double currentTime) {
        return currentTime + slowWashStream.next();
    }
}