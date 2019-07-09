/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.rest.builder;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.BitstreamFormat;
import org.dspace.core.Context;
import org.dspace.discovery.SearchServiceException;
import org.dspace.service.DSpaceCRUDService;

/**
 * Created by jonas - jonas@atmire.com on 04/12/17.
 */
public class BitstreamFormatBuilder extends AbstractCRUDBuilder<BitstreamFormat> {

    /* Log4j logger*/
    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(BitstreamFormatBuilder.class);

    private BitstreamFormat bitstreamFormat;

    protected BitstreamFormatBuilder(Context context) {
        super(context);
    }

    @Override
    public void cleanup() throws Exception {
        delete(bitstreamFormat);
    }

    @Override
    protected DSpaceCRUDService<BitstreamFormat> getService() {
        return bitstreamFormatService;
    }

    @Override
    public BitstreamFormat build() {
        try {

            bitstreamFormatService.update(context, bitstreamFormat);
            context.dispatchEvents();

            indexingService.commit();
        } catch (SearchServiceException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        } catch (AuthorizeException e) {
            log.error(e);
            ;
        }
        return bitstreamFormat;
    }


    public static BitstreamFormatBuilder createBitstreamFormat(Context context)
        throws SQLException, AuthorizeException {
        BitstreamFormatBuilder bitstreamFormatBuilder = new BitstreamFormatBuilder(context);
        return bitstreamFormatBuilder.create(context);
    }

    private BitstreamFormatBuilder create(Context context) throws SQLException, AuthorizeException {
        this.context = context;

        bitstreamFormat = bitstreamFormatService.create(context);

        return this;
    }

    public BitstreamFormatBuilder withMimeType(String mimeType) {
        bitstreamFormat.setMIMEType(mimeType);
        return this;
    }

    public BitstreamFormatBuilder withDescription(String description) {
        bitstreamFormat.setDescription(description);
        return this;
    }

    public BitstreamFormatBuilder withShortDescription(String description) throws SQLException {
        bitstreamFormat.setShortDescription(context, description);
        return this;
    }

    /**
     * Delete the Test BitstreamFormat referred to by the given UUID
     * @param id Id of Test BitstreamFormat to delete
     * @throws SQLException
     * @throws IOException
     */
    public static void deleteBitstreamFormat(int id) throws SQLException, IOException {
        try (Context c = new Context()) {
            c.turnOffAuthorisationSystem();
            BitstreamFormat bitstreamFormat = bitstreamFormatService.find(c, id);
            if (bitstreamFormat != null) {
                try {
                    bitstreamFormatService.delete(c, bitstreamFormat);
                } catch (AuthorizeException e) {
                    // cannot occur, just wrap it to make the compiler happy
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            c.complete();
        }
    }

}
