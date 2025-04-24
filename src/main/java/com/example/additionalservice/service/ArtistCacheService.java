package com.example.additionalservice.service;

import com.example.additionalservice.model.Artist;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ArtistCacheService {

    private final Map<Long, Artist> artistCache = new ConcurrentHashMap<>();

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
