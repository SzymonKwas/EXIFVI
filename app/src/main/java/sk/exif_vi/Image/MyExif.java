package sk.exif_vi.Image;

import android.media.ExifInterface;

public class MyExif {

    public MyExif(ExifInterface exifInterface) {

        setExif_DATETIME(exifInterface.getAttribute(ExifInterface.TAG_DATETIME));
        setExif_FLASH(exifInterface.getAttribute(ExifInterface.TAG_FLASH));
        setExif_FOCAL_LENGTH(exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH));
        setExif_GPS_DATESTAMP(exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP));
        setExif_GPS_LATITUDE(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
        setExif_GPS_LATITUDE_REF(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
        setExif_GPS_LONGITUDE(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
        setExif_GPS_LONGITUDE_REF(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
        setExif_GPS_PROCESSING_METHOD(exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD));
        setExif_GPS_TIMESTAMP(exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP));
        setExif_IMAGE_LENGTH(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
        setExif_IMAGE_WIDTH(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
        setExif_MAKE(exifInterface.getAttribute(ExifInterface.TAG_MAKE));
        setExif_MODEL(exifInterface.getAttribute(ExifInterface.TAG_MODEL));
        setExif_ORIENTATION(exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));
        setExif_WHITE_BALANCE(exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE));
    }

    private String exif_DATETIME = "";
    private String exif_FLASH = "";
    private String exif_FOCAL_LENGTH = "";
    private String exif_GPS_DATESTAMP = "";
    private String exif_GPS_LATITUDE = "";
    private String exif_GPS_LATITUDE_REF = "";
    private String exif_GPS_LONGITUDE = "";
    private String exif_GPS_LONGITUDE_REF = "";
    private String exif_GPS_PROCESSING_METHOD = "";
    private String exif_GPS_TIMESTAMP = "";
    private String exif_IMAGE_LENGTH = "";
    private String exif_IMAGE_WIDTH = "";
    private String exif_MAKE = "";
    private String exif_MODEL = "";
    private String exif_ORIENTATION = "";
    private String exif_WHITE_BALANCE = "";

    public String getExif_DATETIME() {
        return exif_DATETIME;
    }

    public void setExif_DATETIME(String exif_DATETIME) {
        this.exif_DATETIME = exif_DATETIME;
    }

    public String getExif_FLASH() {
        return exif_FLASH;
    }

    public void setExif_FLASH(String exif_FLASH) {
        this.exif_FLASH = exif_FLASH;
    }

    public String getExif_FOCAL_LENGTH() {
        return exif_FOCAL_LENGTH;
    }

    public void setExif_FOCAL_LENGTH(String exif_FOCAL_LENGTH) {
        this.exif_FOCAL_LENGTH = exif_FOCAL_LENGTH;
    }

    public String getExif_GPS_DATESTAMP() {
        return exif_GPS_DATESTAMP;
    }

    public void setExif_GPS_DATESTAMP(String exif_GPS_DATESTAMP) {
        this.exif_GPS_DATESTAMP = exif_GPS_DATESTAMP;
    }

    public String getExif_GPS_LATITUDE() {
        return exif_GPS_LATITUDE;
    }

    public void setExif_GPS_LATITUDE(String exif_GPS_LATITUDE) {
        this.exif_GPS_LATITUDE = exif_GPS_LATITUDE;
    }

    public String getExif_GPS_LATITUDE_REF() {
        return exif_GPS_LATITUDE_REF;
    }

    public void setExif_GPS_LATITUDE_REF(String exif_GPS_LATITUDE_REF) {
        this.exif_GPS_LATITUDE_REF = exif_GPS_LATITUDE_REF;
    }

    public String getExif_GPS_LONGITUDE() {
        return exif_GPS_LONGITUDE;
    }

    public void setExif_GPS_LONGITUDE(String exif_GPS_LONGITUDE) {
        this.exif_GPS_LONGITUDE = exif_GPS_LONGITUDE;
    }

    public String getExif_GPS_LONGITUDE_REF() {
        return exif_GPS_LONGITUDE_REF;
    }

    public void setExif_GPS_LONGITUDE_REF(String exif_GPS_LONGITUDE_REF) {
        this.exif_GPS_LONGITUDE_REF = exif_GPS_LONGITUDE_REF;
    }

    public String getExif_GPS_PROCESSING_METHOD() {
        return exif_GPS_PROCESSING_METHOD;
    }

    public void setExif_GPS_PROCESSING_METHOD(String exif_GPS_PROCESSING_METHOD) {
        this.exif_GPS_PROCESSING_METHOD = exif_GPS_PROCESSING_METHOD;
    }

    public String getExif_GPS_TIMESTAMP() {
        return exif_GPS_TIMESTAMP;
    }

    public void setExif_GPS_TIMESTAMP(String exif_GPS_TIMESTAMP) {
        this.exif_GPS_TIMESTAMP = exif_GPS_TIMESTAMP;
    }

    public String getExif_IMAGE_LENGTH() {
        return exif_IMAGE_LENGTH;
    }

    public void setExif_IMAGE_LENGTH(String exif_IMAGE_LENGTH) {
        this.exif_IMAGE_LENGTH = exif_IMAGE_LENGTH;
    }

    public String getExif_IMAGE_WIDTH() {
        return exif_IMAGE_WIDTH;
    }

    public void setExif_IMAGE_WIDTH(String exif_IMAGE_WIDTH) {
        this.exif_IMAGE_WIDTH = exif_IMAGE_WIDTH;
    }

    public String getExif_MAKE() {
        return exif_MAKE;
    }

    public void setExif_MAKE(String exif_MAKE) {
        this.exif_MAKE = exif_MAKE;
    }

    public String getExif_MODEL() {
        return exif_MODEL;
    }

    public void setExif_MODEL(String exif_MODEL) {
        this.exif_MODEL = exif_MODEL;
    }

    public String getExif_ORIENTATION() {
        return exif_ORIENTATION;
    }

    public void setExif_ORIENTATION(String exif_ORIENTATION) {
        this.exif_ORIENTATION = exif_ORIENTATION;
    }

    public String getExif_WHITE_BALANCE() {
        return exif_WHITE_BALANCE;
    }

    public void setExif_WHITE_BALANCE(String exif_WHITE_BALANCE) {
        this.exif_WHITE_BALANCE = exif_WHITE_BALANCE;
    }

    @Override
    public String toString() {
        return(
                "Date Time: " + exif_DATETIME + "\n" +
                "Flash: " + exif_FLASH + "\n" +
                "Focal Length: " + exif_FOCAL_LENGTH + "\n" +
                "GPS Date Stamp: " + exif_GPS_DATESTAMP + "\n" +
                "GPS Latitude: " + exif_GPS_LATITUDE + "\n" +
                "GPS Latitute Ref: " + exif_GPS_LATITUDE_REF + "\n" +
                "GPS Longitude: " + exif_GPS_LONGITUDE + "\n" +
                "GPS Longitude Ref: " + exif_GPS_LONGITUDE_REF + "\n" +
                "Processing Method: " + exif_GPS_PROCESSING_METHOD + "\n" +
                "GPS Time Stamp: " + exif_GPS_TIMESTAMP + "\n" +
                "Image Length: " + exif_IMAGE_LENGTH + "\n" +
                "Image Width: " + exif_IMAGE_WIDTH + "\n" +
                "Make: " + exif_MAKE + "\n" +
                "Model: " + exif_MODEL + "\n" +
                "Orientation: " + exif_ORIENTATION + "\n" +
                "White Balance: " + exif_WHITE_BALANCE + "\n");
    }
}
