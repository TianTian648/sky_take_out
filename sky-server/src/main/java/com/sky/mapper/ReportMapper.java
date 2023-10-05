package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {
    Double turnoverStatistics(Map map);

    Integer UserStatistics(LocalDateTime begin, LocalDateTime end);

    Integer OrderStatistics(LocalDateTime begin, LocalDateTime end, Integer status);
    @Select("SELECT id from sky_take_out.orders where order_time >= #{begin} and order_time <= #{end}")
    List<Long> list(LocalDateTime begin, LocalDateTime end);

    List<GoodsSalesDTO>  SalesTop(List<Long> ids);
}
