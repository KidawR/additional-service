package com.example.additionalservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorStatsExtended {
    private Long artistId;
    private String artistName;
    private Map<String, Long> sectorViewerCounts;
}
