/**
 * This code has unlimited rights, and is provided "as is" by the National Centers 
 * for Environmental Prediction, without warranty of any kind, either expressed or implied, 
 * including but not limited to the implied warranties of merchantability and/or fitness 
 * for a particular purpose.
 * 
 * This code has been developed by the NCEP-SIB for use in the AWIPS2 system.
 * 
 **/
package gov.noaa.nws.ncep.viz.ui.editedregions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import gov.noaa.nws.ncep.viz.ui.display.NcDisplayMngr;
import gov.noaa.nws.ncep.viz.ui.editedregions.dialog.EditRegionsDialog;

/**
 * Handler to pop up the edit events window in CAVE.
 * 
 * <pre>
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * 01/15        R9583      sgurung   Initial creation.
 * 
 * </pre>
 * 
 * @author sgurung
 * @version 1.0
 * 
 */
public class EditRegionsDialogHandler extends AbstractHandler {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.
     * commands .ExecutionEvent)
     */
    @Override
    public Object execute(ExecutionEvent arg0) throws ExecutionException {

        EditRegionsDialog dlg = EditRegionsDialog
                .getInstance(NcDisplayMngr.getCaveShell());
        dlg.open();
        return null;

    }

}
