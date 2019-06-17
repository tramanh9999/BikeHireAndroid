package com.fpt.model;



import java.util.Date;

public class BikeSlot {


    public Date getSlot_from() {
        return slot_from;
    }

    public void setSlot_from(Date slot_from) {
        this.slot_from = slot_from;
    }


    Date slot_from;
    Date slot_to;

    public BikeSlot() {
    }


    public BikeSlot(Date from, Date to) {
        this.slot_from = from;
        this.slot_to = to;
    }

    public Date getSlot_to() {
        return slot_to;
    }

    public void setSlot_to(Date slot_to) {
        this.slot_to = slot_to;
    }
}
