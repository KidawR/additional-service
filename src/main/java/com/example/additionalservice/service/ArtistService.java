package com.example.additionalservice.service;

import com.example.additionalservice.model.Artist;
import com.example.additionalservice.service.clients.ArtistClient;
import com.example.additionalservice.service.statistics.ObservabilityService;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private final ArtistClient artistClient;
    private final RedisCache redisCache; // Используем RedisCache для кэширования
    private final ObservabilityService observabilityService;

    public ArtistService(ArtistClient artistClient, RedisCache redisCache, ObservabilityService observabilityService) {
        this.artistClient = artistClient;
        this.redisCache = redisCache; // Инициализируем RedisCache
        this.observabilityService = observabilityService;
    }

    // Получение артиста из кэша или загрузка и добавление в кэш
    public Artist getArtist(Long artistId) {
        this.observabilityService.start(getClass().getSimpleName() + ":getArtist");

        // Попытка получить артиста из кэша
        Artist artist = redisCache.get(artistId).orElse(null);

        // Если артиста нет в кэше, загружаем его через клиента и добавляем в кэш
        if (artist == null) {
            artist = artistClient.getArtistById(artistId);
            if (artist != null) {
                redisCache.put(artist); // Добавляем артиста в кэш
            }
        }

        this.observabilityService.stop(getClass().getSimpleName() + ":getArtist");
        return artist;
    }
}
