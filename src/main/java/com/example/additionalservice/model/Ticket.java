package com.example.additionalservice.model;

import io.micrometer.common.lang.NonNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {

    @NonNull
    private long id;
    @NonNull
    public long artistId;
    @NonNull
    public long viewerId;
    @NonNull
    public String date;
    public String sector;
    public Ticket(long id, long artistId, long viewerId, String date, String sector) {
        this.id = id;
        this.artistId = artistId;
        this.viewerId = viewerId;
        this.sector = sector;
        this.date = date;
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", artistId=" + artistId +
                ", viewerId=" + viewerId +
                ", date='" + date + '\'' +
                ", sector=" + sector +
                '}';
    }
}
