package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Map;

@Mapper
public interface ReportMapper {
    Double turnoverStatistics(Map map);

    Integer UserStatistics(LocalDateTime begin, LocalDateTime end);

    Integer OrderStatistics(LocalDateTime begin, LocalDateTime end, Integer status);
}
