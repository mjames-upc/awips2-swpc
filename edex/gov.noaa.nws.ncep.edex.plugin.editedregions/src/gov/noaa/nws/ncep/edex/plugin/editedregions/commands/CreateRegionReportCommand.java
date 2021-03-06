package gov.noaa.nws.ncep.edex.plugin.editedregions.commands;

import java.util.Calendar;

import com.raytheon.uf.common.dataplugin.PluginException;
import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.UFStatus;
import com.raytheon.uf.edex.database.DataAccessLayerException;

import gov.noaa.nws.ncep.common.dataplugin.editedregions.RegionReport;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.exception.EditedRegionsException;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.request.CreateRegionHistoryReportRequest;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.request.CreateRegionReportRequest;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.request.intf.IRequest;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.response.CreateRegionHistoryReportResponse;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.response.CreateRegionReportResponse;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.response.intf.IResponse;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.results.CreateRegionHistoryReportResults;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.results.CreateRegionReportResults;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.util.EditedRegionsConstants;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.util.EditedRegionsConstants.REGION_REPORT_CHANGE_TYPE;
import gov.noaa.nws.ncep.edex.plugin.editedregions.dao.RegionReportsDao;

/**
 * The command class that is executed to add a region report.
 * 
 * 
 * @author jtravis
 * @version 1.0
 */
public class CreateRegionReportCommand extends BaseCommand {

    /**
     * The request from the client that resulted in creating an instance of the
     * command
     */
    private CreateRegionReportRequest request = null;

    /**
     * Dao for EventBin records
     */
    private RegionReportsDao regionReportsDao = null;

    /**
     * Logger
     */
    private static final IUFStatusHandler statusHandler = UFStatus
            .getHandler(CreateRegionReportCommand.class);

    /**
     * Default Constructor
     */
    public CreateRegionReportCommand() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedregions.commands.intf.ICommand#
     * getError()
     */
    @Override
    public EditedRegionsException getError() {
        return this.error;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedregions.commands.intf.ICommand#
     * hasError()
     */
    @Override
    public boolean hasError() {
        if (this.error == null) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedregions.commands.intf.ICommand#
     * getStartTime()
     */
    @Override
    public long getStartTime() {
        return this.startTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedregions.commands.intf.ICommand#
     * getEndTime()
     */
    @Override
    public long getEndTime() {
        return this.endTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedregions.commands.intf.ICommand#
     * getProcessingTime()
     */
    @Override
    public long getProcessingTime() {
        long time = 0L;

        time = this.getEndTime() - this.getStartTime();

        return time;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedregions.commands.intf.ICommand#
     * isValid()
     */
    @Override
    public boolean isValid() {
        if (this.request != null && this.request.isValid()) {

            // a request is associated with the command
            // and the request is valid
            return true;

        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedregions.commands.intf.ICommand#
     * setRequest(gov.noaa.nws.ncep.common.dataplugin.editedregions.request.intf
     * .IRequest)
     */
    @Override
    public void setRequest(IRequest request) {
        this.request = (CreateRegionReportRequest) request;

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedregions.commands.intf.ICommand#
     * getRequest()
     */
    @Override
    public IRequest getRequest() {
        return (IRequest) this.request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedregions.commands.intf.ICommand#
     * execute()
     */
    @Override
    public IResponse execute() {

        statusHandler
                .info("Starting Executing " + this.getClass().getSimpleName());

        this.setStartTime();

        RegionReport report = this.request.getRegionReport();
        int reportId = 0;

        try {

            // Record the new RegionReport.

            this.regionReportsDao = new RegionReportsDao();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(EditedRegionsConstants.TIME_ZONE_UTC);
            calendar.setTimeInMillis(System.currentTimeMillis());
            report.setObservationTime(calendar.getTime());

            reportId = this.regionReportsDao.persist(report);
            report.setId(reportId);

            // TODO - add logic to add a Region History Report indicating
            // that the Region Report was created
            //
            // CreateRegionHistoryReportCommand cmd = new
            // CreateRegionHistoryReportCommand();
            // supply the required request parameters
            // response = cmd.execute()
            // if (response.success) {

            // Record the history

            CreateRegionHistoryReportRequest historyRequest = new CreateRegionHistoryReportRequest();
            historyRequest.setChangeType(REGION_REPORT_CHANGE_TYPE.CREATE);
            historyRequest.setNewReport(report);
            historyRequest.setRegionReportId(reportId);

            CreateRegionHistoryReportCommand historyCommand = new CreateRegionHistoryReportCommand();
            historyCommand.setRequest(historyRequest);
            CreateRegionHistoryReportResponse historyResponse = (CreateRegionHistoryReportResponse) historyCommand
                    .execute();
            CreateRegionHistoryReportResults historyResults = (CreateRegionHistoryReportResults) historyResponse
                    .getResults();

            if (!historyResults.isSuccessful()) {
                setError(new EditedRegionsException(
                        "There was a problem recording history."));
            }

        } catch (PluginException e) {
            setError(new EditedRegionsException(e));
        } catch (DataAccessLayerException e) {
            setError(new EditedRegionsException(e));
        }

        this.setEndTime();

        statusHandler
                .info("Finish Executing " + this.getClass().getSimpleName());

        // change return statemet to this:
        // return this.createResponse(report, reportHistoryCreated)

        return this.createResponse(report);
    }

    /**
     * @param results
     * @return IResponse
     */
    private IResponse createResponse(RegionReport report) {
        CreateRegionReportResponse response = new CreateRegionReportResponse();

        if (this.hasError()) {
            response.setError(this.getError());
        } else {
            CreateRegionReportResults results = new CreateRegionReportResults();
            results.setReportID(report.getId());
            response.setResults(results);
        }

        response.setRequest(this.getRequest());
        response.setProcessingTime(this.getProcessingTime());
        return response;
    }

}
