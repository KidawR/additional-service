package com.example.additionalservice.service;


import com.example.additionalservice.model.Artist;
import com.example.additionalservice.model.SectorStatsExtended;
import com.example.additionalservice.model.Ticket;
import com.example.additionalservice.service.clients.ArtistClient;
import com.example.additionalservice.service.clients.TicketClient;
import com.example.additionalservice.service.statistics.ObservabilityService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private final TicketClient ticketClient;
    private final ArtistClient artistClient;
    private final ObservabilityService observabilityService;
    public StatsService(TicketClient ticketClient, ArtistClient artistClient, ObservabilityService observabilityService) {
        this.ticketClient = ticketClient;
        this.artistClient = artistClient;
        this.observabilityService = observabilityService;
    }

    public List<SectorStatsExtended> getSectorStatsByArtistAndSector() {
        this.observabilityService.start(getClass().getSimpleName() + ":getStatsByArtist");
        Ticket[] tickets = ticketClient.getAllTickets();
        Artist[] artists = artistClient.getAllArtists();

        if (tickets == null || artists == null) return List.of();

        Map<Long, Artist> artistMap = Arrays.stream(artists)
                .collect(Collectors.toMap(Artist::getId, a -> a));

        Map<String, Long> grouped = Arrays.stream(tickets)
                .collect(Collectors.groupingBy(
                        t -> t.getArtistId() + "#" + t.getSector(),
                        Collectors.counting()
                ));

        List<SectorStatsExtended> temp = grouped.entrySet().stream().map(entry -> {
            String[] parts = entry.getKey().split("#");
            Long artistId = Long.valueOf(parts[0]);
            String sector = parts[1];
            Artist artist = artistMap.get(artistId);
            return new SectorStatsExtended(
                    artistId,
                    artist != null ? artist.getNameGroup() : "Unknown",
                    sector,
                    entry.getValue()
            );
        }).collect(Collectors.toList());
        this.observabilityService.stop(getClass().getSimpleName() + ":getStatsByArtist");
        return temp;
    }
}
