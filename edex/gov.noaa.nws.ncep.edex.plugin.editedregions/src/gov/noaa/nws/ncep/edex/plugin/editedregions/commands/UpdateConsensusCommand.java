package gov.noaa.nws.ncep.edex.plugin.editedregions.commands;

import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.UFStatus;

import gov.noaa.nws.ncep.common.dataplugin.editedregions.exception.EditedRegionsException;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.request.UpdateConsensusRequest;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.request.intf.IRequest;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.response.UpdateConsensusResponse;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.response.intf.IResponse;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.results.UpdateConsensusResults;
import gov.noaa.nws.ncep.common.dataplugin.editedregions.results.intf.IResults;

/**
 * 
 * The command used to update the swpc_region_consensus table with new consensus
 * values.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Mar 19, 2016  R9583     sgurung     Initial creation
 * 
 * </pre>
 * 
 * @author alockleigh
 * @version 1.0
 */
public class UpdateConsensusCommand extends BaseCommand {

    /**
     * The request from the client that resulted in creating an instance of the
     * command
     */
    private UpdateConsensusRequest request = null;

    /**
     * 
     */
    // private EventsDao eventsDao = null;

    /**
     * Logger
     */
    private static final IUFStatusHandler statusHandler = UFStatus
            .getHandler(UpdateConsensusCommand.class);

    public UpdateConsensusCommand() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#
     * getError()
     */
    @Override
    public EditedRegionsException getError() {
        return this.error;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#
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
     * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#
     * getStartTime()
     */
    @Override
    public long getStartTime() {
        return this.startTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#
     * getEndTime()
     */
    @Override
    public long getEndTime() {
        return this.endTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#
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
     * @see
     * gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#isValid
     * ()
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
     * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#
     * setRequest(gov.noaa.nws.ncep.common.dataplugin.editedevents.request.intf.
     * IRequest)
     */
    @Override
    public void setRequest(IRequest request) {
        this.request = (UpdateConsensusRequest) request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#
     * getRequest()
     */
    @Override
    public IRequest getRequest() {
        return this.request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#execute
     * ()
     */
    @Override
    public IResponse execute() {

        statusHandler
                .info("Starting Executing " + this.getClass().getSimpleName());

        this.setStartTime();

        UpdateConsensusResults results = new UpdateConsensusResults();

        // try {
        // eventsDao = new EventsDao();
        //
        // // Dump deleted events
        // int numRecordsDeleted =
        // eventsDao.removeDeletedEvents(this.request.getBeginDTTM());
        //
        // // Change events age to 'OLD' for events whose age is 'NEW' or 'COR'
        // int numRecordsUpdated =
        // eventsDao.setEventsAgeToOld(this.request.getBeginDTTM());
        //
        // // Set changeflag to 0
        // numRecordsUpdated +=
        // eventsDao.setChangeFlagToZero(this.request.getBeginDTTM());
        //
        // results.setNumRecordsDeleted(numRecordsDeleted);
        // results.setNumRecordsUpdated(numRecordsUpdated);
        //
        // } catch (Exception e) {
        // error = new EditedRegionsException(
        // "ERROR - Exception occured while executing the ExitCommand.",e);
        // e.printStackTrace();
        // }

        this.setEndTime();

        statusHandler
                .info("Finish Executing " + this.getClass().getSimpleName());

        return createResponse(results);
    }

    /**
     * Create a response based on the results provided
     * 
     * @param results
     * @return IResponse
     */
    private IResponse createResponse(IResults results) {
        UpdateConsensusResponse response = new UpdateConsensusResponse();

        if (this.hasError()) {
            response.setError(this.getError());
        } else {
            response.setResults(results);
        }

        // populate the response and the results
        response.setRequest(this.getRequest());
        response.setProcessingTime(this.getProcessingTime());

        return response;

    }

}
