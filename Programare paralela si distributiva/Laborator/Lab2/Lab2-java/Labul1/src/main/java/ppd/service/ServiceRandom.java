package ppd.service;

import ppd.domain.ComplexNumber;

/**
 * Created by toade on 10/5/2017.
 */
public interface ServiceRandom {
    int getRandomInteger(int aStart, int aEnd);
    double getRandomDouble(int aStart, int aEnd);
    ComplexNumber getRandomComplexNumebr(int aStart, int aEnd);
}
