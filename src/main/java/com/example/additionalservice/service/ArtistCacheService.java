package com.example.additionalservice.service;

import com.example.additionalservice.model.Artist;
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

    public ArtistCacheService() {
        scheduler.scheduleAtFixedRate(() ->
                System.out.println("[Artist Cache] Current size: " + artistCache.size()), 0, 30, TimeUnit.SECONDS);
    }
    public Artist getArtist(Long artistId) {
        return artistCache.get(artistId);
    }

    public void addArtist(Long artistId, Artist artist) {
        artistCache.put(artistId, artist);
    }

    public boolean containsArtist(Long artistId) {
        return artistCache.containsKey(artistId);
    }

    public int cacheSize() {
        return artistCache.size();
    }
}
