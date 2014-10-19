package org.aua.sample.server;

import java.util.Date;

/**
 * Created by Armen on 19-Oct-14.
 */
public class ArchiveRecord {
    private String owner;
    private Sale sale = null;
    private Date date = null;

    public ArchiveRecord(Sale sale){
        this.owner = sale.getShopingCart().getOwner();
        this.sale = sale;
        this.date = new Date();
    }

    /**
     *
     * @return
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     *
     * @return
     */
    public Sale getSale() {
        return sale;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

}
