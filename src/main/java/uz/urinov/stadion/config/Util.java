package uz.urinov.stadion.config;

import java.time.LocalTime;

public class Util {

    public static boolean timeBetweenTwoTimes(
            LocalTime aStart, LocalTime aEnd,
            LocalTime bStart, LocalTime bend
    ) {

        return aEnd.isBefore(bStart) || aStart.isAfter(bend);
    }

}
