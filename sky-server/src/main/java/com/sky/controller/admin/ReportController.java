package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@Api(tags = "报表相关接口")
@RequestMapping("/admin/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 营业额统计
     *
     * @param begin
     * @param end
     * @return
     */
    @ApiOperation("营业额统计")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
           return Result.success(reportService.turnoverStatistics(begin, end));
    }

    /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    @ApiOperation("用户统计")
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
     return Result.success(reportService.userStatistics(begin, end));
    }

    /**
     * 订单统计
     * @param begin
     * @param end
     * @return
     */
    @ApiOperation("订单统计")
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> orderStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
           return Result.success(reportService.orderStatistics(begin, end));
    }

}
