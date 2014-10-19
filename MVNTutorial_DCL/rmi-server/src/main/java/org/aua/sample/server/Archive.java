package org.aua.sample.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Armen on 11-Oct-14.
 */
public class Archive {
    private Map<String,ArchiveRecord> archiveRecords = new HashMap<>();

    public void endSale(String username, Sale sale){
        archiveRecords.put( username, new ArchiveRecord(sale) );
    }

    public Map<String,ArchiveRecord> getArchiveRecords(){
        return this.archiveRecords;
    }
}
