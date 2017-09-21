package gov.noaa.nws.ncep.common.dataplugin.editedregions.results;

import java.util.Date;

import com.raytheon.uf.common.serialization.annotations.DynamicSerialize;

import gov.noaa.nws.ncep.common.dataplugin.editedregions.results.intf.IResults;

/**
 * Class that holds the results from execution of the CreateRegion command
 * 
 * <pre>
 *
 * SOFTWARE HISTORY
 *
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Jul 6, 2017            jtravis     Initial creation
 *
 * </pre>
 *
 * @author jtravis
 */
@DynamicSerialize
public class GetConsensusTodaysResults implements IResults {

    private Date observationTime;

    private String observatory;

    private Integer quality;

    private Integer region;

    private Integer latitude;

    private Integer longitude;

    private Integer carlon;

    private Integer extent;

    private Integer area;

    private Integer numspots;

    private Integer magcode;

    private Integer penumbra;

    private Integer zurich;

    private Integer compact;

    /**
     * Constructor
     */
    public GetConsensusTodaysResults() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.noaa.nws.ncep.common.dataplugin.editedregions.results.intf.IResults#
     * numResults()
     */
    @Override
    public int numResults() {
        return 0;
    }

    /**
     * @return the observationTime
     */
    public Date getObservationTime() {
        return observationTime;
    }

    /**
     * @param observationTime
     *            the observationTime to set
     */
    public void setObservationTime(Date observationTime) {
        this.observationTime = observationTime;
    }

    /**
     * @return the observatory
     */
    public String getObservatory() {
        return observatory;
    }

    /**
     * @param observatory
     *            the observatory to set
     */
    public void setObservatory(String observatory) {
        this.observatory = observatory;
    }

    /**
     * @return the quality
     */
    public Integer getQuality() {
        return quality;
    }

    /**
     * @param quality
     *            the quality to set
     */
    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    /**
     * @return the region
     */
    public Integer getRegion() {
        return region;
    }

    /**
     * @param region
     *            the region to set
     */
    public void setRegion(Integer region) {
        this.region = region;
    }

    /**
     * @return the latitude
     */
    public Integer getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Integer getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the carlon
     */
    public Integer getCarlon() {
        return carlon;
    }

    /**
     * @param carlon
     *            the carlon to set
     */
    public void setCarlon(Integer carlon) {
        this.carlon = carlon;
    }

    /**
     * @return the extent
     */
    public Integer getExtent() {
        return extent;
    }

    /**
     * @param extent
     *            the extent to set
     */
    public void setExtent(Integer extent) {
        this.extent = extent;
    }

    /**
     * @return the area
     */
    public Integer getArea() {
        return area;
    }

    /**
     * @param area
     *            the area to set
     */
    public void setArea(Integer area) {
        this.area = area;
    }

    /**
     * @return the numspots
     */
    public Integer getNumspots() {
        return numspots;
    }

    /**
     * @param numspots
     *            the numspots to set
     */
    public void setNumspots(Integer numspots) {
        this.numspots = numspots;
    }

    /**
     * @return the penumbra
     */
    public Integer getPenumbra() {
        return penumbra;
    }

    /**
     * @param penumbra
     *            the penumbra to set
     */
    public void setPenumbra(Integer penumbra) {
        this.penumbra = penumbra;
    }

    /**
     * @return the zurich
     */
    public Integer getZurich() {
        return zurich;
    }

    /**
     * @param zurich
     *            the zurich to set
     */
    public void setZurich(Integer zurich) {
        this.zurich = zurich;
    }

    /**
     * @return the compact
     */
    public Integer getCompact() {
        return compact;
    }

    /**
     * @param compact
     *            the compact to set
     */
    public void setCompact(Integer compact) {
        this.compact = compact;
    }

    /**
     * @return the magcode
     */
    public Integer getMagcode() {
        return magcode;
    }

    /**
     * @param magcode
     *            the magcode to set
     */
    public void setMagcode(Integer magcode) {
        this.magcode = magcode;
    }

}