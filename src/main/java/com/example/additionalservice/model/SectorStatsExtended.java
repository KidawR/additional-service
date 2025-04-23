package com.example.additionalservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorStatsExtended {
    private Long artistId;
    private String artistName;
    private String sector;
    private Long viewerCount;
}
