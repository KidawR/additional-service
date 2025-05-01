package com.example.additionalservice.service;

import com.example.additionalservice.model.Artist;
import com.example.additionalservice.service.statistics.ObservabilityService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ArtistCacheService {
    private final Map<Long, Artist> artistCache = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private final ObservabilityService observabilityService;

    public void clearCache(){
        this.artistCache.clear();
    }
    public ArtistCacheService(ObservabilityService observabilityService) {
        this.observabilityService = observabilityService;
        //scheduler.scheduleAtFixedRate(() ->
        //        System.out.println("[Artist Cache] Current size: " + this.cacheSize()), 0, 30, TimeUnit.SECONDS);
    }
    public Artist getArtist(Long artistId) {

        this.observabilityService.start(getClass().getSimpleName() + ":getArtistCache");
        Artist temp = artistCache.get(artistId);
        this.observabilityService.stop(getClass().getSimpleName() + ":getArtistCache");
        return temp;
    }

    public void addArtist(Long artistId, Artist artist) {

        this.observabilityService.start(getClass().getSimpleName() + ":addArtistCache");
        artistCache.put(artistId, artist);
        this.observabilityService.stop(getClass().getSimpleName() + ":addArtistCache");
    }

    public boolean containsArtist(Long artistId) {

        this.observabilityService.start(getClass().getSimpleName() + ":containsArtistCache");
        boolean temp = artistCache.containsKey(artistId);
        this.observabilityService.stop(getClass().getSimpleName() + ":containsArtistcCache");
        return temp;
    }

    public int cacheSize() {
        this.observabilityService.start(getClass().getSimpleName() + ":cacheSize");
        int temp = artistCache.size();
        this.observabilityService.stop(getClass().getSimpleName() + ":cacheSize");
        return temp;
    }
}
