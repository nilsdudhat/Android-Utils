package com.udemy.javaexample.utils;

import android.media.ExifInterface;

public class LocationUtils {

    public static String getLatLongFromEXIF(ExifInterface exif) {

        String LATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
        String LATITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
        String LONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
        String LONGITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

        // your Final lat Long Values
        Float Latitude, Longitude;

        if ((LATITUDE != null)
                && (LATITUDE_REF != null)
                && (LONGITUDE != null)
                && (LONGITUDE_REF != null)) {

            if (LATITUDE_REF.equals("N")) {
                Latitude = convertToDegree(LATITUDE);
            } else {
                Latitude = 0 - convertToDegree(LATITUDE);
            }

            if (LONGITUDE_REF.equals("E")) {
                Longitude = convertToDegree(LONGITUDE);
            } else {
                Longitude = 0 - convertToDegree(LONGITUDE);
            }

            return Latitude + "," + Longitude;
        } else {
            return "";
        }
    }

    private static Float convertToDegree(String stringDMS) {
        float result;

        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = Double.valueOf(stringD[0]);
        Double D1 = Double.valueOf(stringD[1]);
        double FloatD = D0 / D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = Double.valueOf(stringM[0]);
        Double M1 = Double.valueOf(stringM[1]);
        double FloatM = M0 / M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = Double.valueOf(stringS[0]);
        Double S1 = Double.valueOf(stringS[1]);
        double FloatS = S0 / S1;

        result = (float) (FloatD + (FloatM / 60) + (FloatS / 3600));

        return result;
    }
}
