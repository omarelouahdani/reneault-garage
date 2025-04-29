package org.sid.reneaultgarage.mapper.impl;

import org.sid.reneaultgarage.dto.GarageDto;
import org.sid.reneaultgarage.dto.OpeningTimeDto;
import org.sid.reneaultgarage.mapper.GarageMapper;
import org.sid.reneaultgarage.model.Garage;
import org.sid.reneaultgarage.model.OpeningTime;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GarageMapperImpl implements GarageMapper {

    @Override
    public GarageDto toDto(Garage entity) {
        if (entity == null) return null;
        GarageDto dto = new GarageDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setTelephone(entity.getTelephone());
        dto.setEmail(entity.getEmail());

        Map<String, List<OpeningTimeDto>> map = new HashMap<>();
        if (entity.getOpeningTimes() != null) {
            // // entity.getOpeningTimes() is List<OpeningTime>, on groupe par dayOfWeek
            Map<DayOfWeek, List<OpeningTime>> grouped = entity.getOpeningTimes().stream()
                    .collect(Collectors.groupingBy(OpeningTime::getDayOfWeek));
            grouped.forEach((day, times) -> {
                List<OpeningTimeDto> dtos = times.stream()
                        .map(ot -> OpeningTimeDto.builder()
                                .dayOfWeek(ot.getDayOfWeek())
                                .startTime(ot.getStartTime())
                                .endTime(ot.getEndTime())
                                .build())
                        .collect(Collectors.toList());
                map.put(day.name(), dtos);
            });
        }
        dto.setOpeningTimes(map);
        return dto;
    }

    @Override
    public Garage toEntity(GarageDto dto) {
        if (dto == null) return null;
        Garage entity = new Garage();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setTelephone(dto.getTelephone());
        entity.setEmail(dto.getEmail());

        Map<DayOfWeek, List<OpeningTime>> map = new HashMap<>();
        entity.setOpeningTimes(new ArrayList<>());
        if (dto.getOpeningTimes() != null) {
            dto.getOpeningTimes().forEach((dayStr, dtos) -> {
                DayOfWeek day = DayOfWeek.valueOf(dayStr);
                List<OpeningTime> times = dtos.stream()
                        .map(d -> {
                            OpeningTime ot = new OpeningTime();
                            ot.setDayOfWeek(day);
                            ot.setStartTime(d.getStartTime());
                            ot.setEndTime(d.getEndTime());
                            ot.setGarage(entity);
                            return ot;
                        })
                        .toList();
                entity.getOpeningTimes().addAll(times);
            });
        }
        return entity;
    }
}
