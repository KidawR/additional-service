package com.example.additionalservice.service;

import com.example.additionalservice.model.SectorStatsExtended;
import com.example.additionalservice.model.Ticket;
import com.example.additionalservice.model.Artist;
import com.example.additionalservice.service.clients.TicketClient;
import com.example.additionalservice.service.statistics.ObservabilityService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class StatsService {

    private final ArtistService artistService;
    private final TicketClient ticketClient;

    private final ObservabilityService observabilityService;
    public StatsService(ArtistService artistService, TicketClient ticketClient, ObservabilityService observabilityService) {
        this.artistService = artistService;
        this.ticketClient = ticketClient;
        this.observabilityService = observabilityService;
    }

    public List<SectorStatsExtended> getSectorStatsByArtistAndSector() {
        this.observabilityService.start(getClass().getSimpleName() + ":getSectorStatsByArtistAndSector");
        Ticket[] tickets = ticketClient.getAllTickets();
        if (tickets == null || tickets.length == 0) {
            return List.of();
        }

        List<SectorStatsExtended> temp = Arrays.stream(tickets)
                .map(ticket -> {
                    Artist artist = artistService.getArtist(ticket.getArtistId());
                    return artist != null ? new AbstractMap.SimpleEntry<>(ticket, artist) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        entry -> entry.getValue().getId(),
                        LinkedHashMap::new,
                        Collectors.groupingBy(
                                entry -> entry.getKey().getSector(),
                                Collectors.counting()
                        )
                ))
                .entrySet().stream()
                .map(entry -> {
                    Long artistId = entry.getKey();
                    Map<String, Long> sectorCounts = entry.getValue();
                    Artist artist = artistService.getArtist(artistId);
                    return new SectorStatsExtended(
                            artistId,
                            artist != null ? artist.getNameGroup() : "Unknown",
                            sectorCounts
                    );
                })
                .collect(Collectors.toList());
        this.observabilityService.stop(getClass().getSimpleName() + ":getSectorStatsByArtistAndSector");
        return temp;
    }

}