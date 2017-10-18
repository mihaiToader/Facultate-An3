package ppd.service.Impl;

import ppd.service.ServiceRandom;

import java.util.Random;

/**
 * Created by toade on 10/5/2017.
 */
public class ServiceRandomImpl implements ServiceRandom {

    private Random random = new Random();

    @Override
    public int getRandomInteger(int aStart, int aEnd) {
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * random.nextDouble());
        return (int)(fraction + aStart);
    }
}
