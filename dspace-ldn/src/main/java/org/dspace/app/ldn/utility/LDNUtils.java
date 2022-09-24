/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.ldn.utility;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.dspace.app.ldn.LDNMetadataFields;
import org.dspace.content.Item;
import org.dspace.content.MetadataValue;
import org.dspace.services.factory.DSpaceServicesFactory;

/**
 * Some linked data notification utilities.
 * 
 * @author William Welling
 * @author Stefano Maffei (4Science.com)
 */
public class LDNUtils {

    private final static Pattern UUID_REGEX_PATTERN = Pattern.compile(
            "\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}");

    private final static String SIMPLE_PROTOCOL_REGEX = "^(http[s]?://www\\.|http[s]?://|www\\.)";

    public final static String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public final static String METADATA_DELIMITER;

    static {
        METADATA_DELIMITER = DSpaceServicesFactory.getInstance()
            .getConfigurationService().getProperty("ldn.metadata.delimiter");
    }

    /**
     * 
     */
    private LDNUtils() {

    }

    /**
     * Whether the URL contains an UUID. Used to determine item id from item URL.
     *
     * @param url item URL
     * @return boolean true if URL has UUID, false otherwise
     */
    public static boolean hasUUIDInURL(String url) {
        Matcher matcher = UUID_REGEX_PATTERN.matcher(url);

        return matcher.find();
    }

    /**
     * Extract UUID from URL.
     *
     * @param url item URL
     * @return UUID item id
     */
    public static UUID getUUIDFromURL(String url) {
        Matcher matcher = UUID_REGEX_PATTERN.matcher(url);
        StringBuilder handle = new StringBuilder();
        if (matcher.find()) {
            handle.append(matcher.group(0));
        }
        return UUID.fromString(handle.toString());
    }

    /**
     * Remove http or https protocol from URL.
     *
     * @param url URL
     * @return String URL without protocol
     */
    public static String removedProtocol(String url) {
        return url.replaceFirst(SIMPLE_PROTOCOL_REGEX, EMPTY);
    }

    public static List<MetadataValue> getMetadataValuesLdnInitialize(Item item) {
        List<MetadataValue> metadataValues = item.getMetadata();
        return metadataValues.stream().filter(metadataValue -> {
            return metadataValue.getMetadataField().getMetadataSchema().getName().equals(LDNMetadataFields.SCHEMA) &&
                metadataValue.getMetadataField().getElement().equals(LDNMetadataFields.ELEMENT) &&
                metadataValue.getMetadataField().getQualifier().equals(LDNMetadataFields.INITIALIZE);
        }).collect(Collectors.toList());
    }

    public static Set<String> getMetadataLdnInitialize(Item item) {
        Set<String> typeSet = getMetadataValuesLdnInitialize(item).stream()
            .map(MetadataValue::getValue).collect(Collectors.toSet());
        return typeSet;
    }

}
