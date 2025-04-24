package com.example.additionalservice.service;
import com.example.additionalservice.model.Artist;
import com.example.additionalservice.service.clients.ArtistClient;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private final ArtistClient artistClient;
    private final ArtistCacheService artistCacheService;

    public ArtistService(ArtistClient artistClient, ArtistCacheService artistCacheService) {
        this.artistClient = artistClient;
        this.artistCacheService = artistCacheService;
    }

    // Получение артиста из кэша или загрузка и добавление в кэш
    public Artist getArtist(Long artistId) {
        // Если артист уже есть в кэше, возвращаем его
        if (artistCacheService.containsArtist(artistId)) {
            return artistCacheService.getArtist(artistId);
        }

        // Если артиста нет в кэше, загружаем его через клиента и добавляем в кэш
        Artist artist = artistClient.getArtistById(artistId);
        if (artist != null) {
            artistCacheService.addArtist(artistId, artist);
        }
        return artist;
    }
}
