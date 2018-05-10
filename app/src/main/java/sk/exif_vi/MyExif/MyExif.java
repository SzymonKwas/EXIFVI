package sk.exif_vi.MyExif;

import android.media.ExifInterface;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

import sk.exif_vi.Utils.CustomParse;
import sk.exif_vi.Utils.DateTimeFormatterUtil;

@Entity
public class MyExif {

    public MyExif(ExifInterface exifInterface, String pathOfFile) {

        this.pathOfFile = pathOfFile;
        setDatetime(DateTimeFormatterUtil.parseStringToDate(exifInterface.getAttribute(ExifInterface.TAG_DATETIME)));
        setFlash(CustomParse.parseLong(exifInterface.getAttribute(ExifInterface.TAG_FLASH)));
        setFocalLength(exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH));
        setGpsDatestamp(exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP));
        setGpsLatitude(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
        setGpsLatitudeRef(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
        setGpsLongitude(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
        setGpsLongitudeRef(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
        setGpsProcessingMethod(exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD));
        setGpsTimestamp(exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP));
        setImageLength(CustomParse.parseLong(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)));
        setImageWidth(CustomParse.parseLong(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)));
        setMake(exifInterface.getAttribute(ExifInterface.TAG_MAKE));
        setModel(exifInterface.getAttribute(ExifInterface.TAG_MODEL));
        setOrientation(CustomParse.parseLong(exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)));
        setWhiteBalance(CustomParse.parseBoolean(exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE)));
    }

    @Generated(hash = 1078695587)
    public MyExif(Long id, Date datetime, Long Flash, String focalLength, String gpsDatestamp, String gpsLatitude,
            String gpsLatitudeRef, String gpsLongitude, String gpsLongitudeRef, String gpsProcessingMethod,
            String gpsTimestamp, Long imageLength, Long imageWidth, String Make, String Model, Long Orientation,
            Boolean whiteBalance, String pathOfFile) {
        this.id = id;
        this.datetime = datetime;
        this.Flash = Flash;
        this.focalLength = focalLength;
        this.gpsDatestamp = gpsDatestamp;
        this.gpsLatitude = gpsLatitude;
        this.gpsLatitudeRef = gpsLatitudeRef;
        this.gpsLongitude = gpsLongitude;
        this.gpsLongitudeRef = gpsLongitudeRef;
        this.gpsProcessingMethod = gpsProcessingMethod;
        this.gpsTimestamp = gpsTimestamp;
        this.imageLength = imageLength;
        this.imageWidth = imageWidth;
        this.Make = Make;
        this.Model = Model;
        this.Orientation = Orientation;
        this.whiteBalance = whiteBalance;
        this.pathOfFile = pathOfFile;
    }

    @Generated(hash = 144161226)
    public MyExif() {
    }

    @Id(autoincrement = true)
    private Long id;
    private Date datetime;
    private Long Flash;
    private String focalLength;
    private String gpsDatestamp;
    private String gpsLatitude;
    private String gpsLatitudeRef;
    private String gpsLongitude;
    private String gpsLongitudeRef;
    private String gpsProcessingMethod;
    private String gpsTimestamp;
    private Long imageLength;
    private Long imageWidth;
    private String Make;
    private String Model;
    private Long Orientation;
    private Boolean whiteBalance;
    private String pathOfFile;

    public void setDatetime(Date Datetime) {
        this.datetime = Datetime;
    }

    public Long getFlash() {
        return Flash;
    }

    public void setFlash(Long Flash) {
        this.Flash = Flash;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String FocalLength) {
        this.focalLength = FocalLength;
    }

    public String getGpsDatestamp() {
        return gpsDatestamp;
    }

    public void setGpsDatestamp(String GpsDatestamp) {
        this.gpsDatestamp = GpsDatestamp;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String GpsLatitude) {
        this.gpsLatitude = GpsLatitude;
    }

    public String getGpsLatitudeRef() {
        return gpsLatitudeRef;
    }

    public void setGpsLatitudeRef(String GpsLatitudeRef) {
        this.gpsLatitudeRef = GpsLatitudeRef;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String GpsLongitude) {
        this.gpsLongitude = GpsLongitude;
    }

    public String getGpsLongitudeRef() {
        return gpsLongitudeRef;
    }

    public void setGpsLongitudeRef(String GpsLongitudeRef) {
        this.gpsLongitudeRef = GpsLongitudeRef;
    }

    public String getGpsProcessingMethod() {
        return gpsProcessingMethod;
    }

    public void setGpsProcessingMethod(String GpsProcessingMethod) {
        this.gpsProcessingMethod = GpsProcessingMethod;
    }

    public String getGpsTimestamp() {
        return gpsTimestamp;
    }

    public void setGpsTimestamp(String GpsTimestamp) {
        this.gpsTimestamp = GpsTimestamp;
    }

    public Long getImageLength() {
        return imageLength;
    }

    public void setImageLength(Long ImageLength) {
        this.imageLength = ImageLength;
    }

    public Long getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Long ImageWidth) {
        this.imageWidth = ImageWidth;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String Make) {
        this.Make = Make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public Long getOrientation() {
        return Orientation;
    }

    public void setOrientation(Long Orientation) {
        this.Orientation = Orientation;
    }

    public Boolean getWhiteBalance() {
        return whiteBalance;
    }

    public void setWhiteBalance(Boolean WhiteBalance) {
        this.whiteBalance = WhiteBalance;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long Id) {
        this.id = Id;
    }

    public Date getDatetime() {
        return this.datetime;
    }

    public String getPathOfFile() {
        return pathOfFile;
    }

    public void setPathOfFile(String pathOfFile) {
        this.pathOfFile = pathOfFile;
    }

    @Override
    public String toString() {
        return (
                "Date Time: " + datetime + "\n" +
                        "Flash: " + Flash + "\n" +
                        "Focal Length: " + focalLength + "\n" +
                        "GPS Date Stamp: " + gpsDatestamp + "\n" +
                        "GPS Latitude: " + gpsLatitude + "\n" +
                        "GPS Latitute Ref: " + gpsLatitudeRef + "\n" +
                        "GPS Longitude: " + gpsLongitude + "\n" +
                        "GPS Longitude Ref: " + gpsLongitudeRef + "\n" +
                        "Processing Method: " + gpsProcessingMethod + "\n" +
                        "GPS Time Stamp: " + gpsTimestamp + "\n" +
                        "Image Length: " + imageLength + "\n" +
                        "Image Width: " + imageWidth + "\n" +
                        "Make: " + Make + "\n" +
                        "Model: " + Model + "\n" +
                        "Orientation: " + Orientation + "\n" +
                        "White Balance: " + whiteBalance + "\n");
    }
}
