package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 根据时间统计营业额
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        //select count(amount) from orders where order_time > begin and order_time < end and status = 5
        List<LocalDate> localDateList = new ArrayList<>();
        localDateList.add(begin);
        log.info("begin:{}", begin);
        log.info("end:{}", end);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
            localDateList.add(begin);
        }

        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate localDate : localDateList) {
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("status", Orders.COMPLETED);
            map.put("begin", beginTime);
            map.put("end", endTime);
            Double turnover = reportMapper.turnoverStatistics(map);
            log.info("turnover:{}", turnover);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
        return TurnoverReportVO.builder()
                .dateList(localDateList.toString())
                .turnoverList(turnoverList.toString())
                .build();
    }

    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> localDateList = new ArrayList<>();
        localDateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            localDateList.add(begin);
        }
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        for (LocalDate date : localDateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            newUserList.add(reportMapper.UserStatistics(beginTime, endTime));
            totalUserList.add(reportMapper.UserStatistics(null, endTime));
        }
        return UserReportVO.builder()
                .dateList(localDateList.toString())
                .totalUserList(totalUserList.toString())
                .newUserList(newUserList.toString())
                .build();
    }

    @Override
    public OrderReportVO orderStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> localDateList = new ArrayList<>();
        localDateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            localDateList.add(begin);
        }
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();
        for (LocalDate date : localDateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            validOrderCountList.add(reportMapper.OrderStatistics(beginTime, endTime, Orders.COMPLETED));
            orderCountList.add(reportMapper.OrderStatistics(beginTime, endTime, null));
        }
        int totalOrderCount = 0;
        int validOrderCount = 0;

        double orderCompletionRate = 0.0;
        for (Integer integer : validOrderCountList) {
            validOrderCount += integer;
        }
        for (Integer integer : orderCountList) {
            totalOrderCount += integer;
        }
        orderCompletionRate = (double) validOrderCount / (double) totalOrderCount;
        return OrderReportVO.builder()
                .dateList(localDateList.toString())
                .orderCountList(orderCountList.toString())
                .validOrderCountList(validOrderCountList.toString())
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    @Override
    public SalesTop10ReportVO Top10Statistics(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        //select id from orders where order_time <= end and order_time >= begin and status = 5 -> list
        List<Long> ids = reportMapper.list(beginTime, endTime);
        log.info("ids:{}", ids);
        //select name, COUNT(name) AS number from orders where order_id = id  GROUP BY name ORDER BY number desc
        List<GoodsSalesDTO> list = reportMapper.SalesTop(ids);
        log.info("map:{}", list);
        List<String> nameList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();
        for (GoodsSalesDTO goodsSalesDTO : list) {
            nameList.add(goodsSalesDTO.getName());
            numberList.add(goodsSalesDTO.getNumber());
        }
        return SalesTop10ReportVO.builder()
                .nameList(nameList.toString())
                .numberList(numberList.toString())
                .build();
    }

    /**
     * 导出近30天的运营数据报表
     *
     * @param response
     **/
    @Override
    public void exportBusinessData(HttpServletResponse response) throws IOException {
        LocalDate begin = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now().minusDays(1);
        //查询概览运营数据，提供给Excel模板文件
        BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        log.info("inputStream: {}" + inputStream);
        //基于提供好的模板文件创建一个新的Excel表格对象
        XSSFWorkbook excel = new XSSFWorkbook(inputStream);
        //获得Excel文件中的一个Sheet页
        XSSFSheet sheet = excel.getSheet("Sheet1");

        sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end);
        //获得第4行
        XSSFRow row = sheet.getRow(3);
        //获取单元格
        row.getCell(2).setCellValue(businessData.getTurnover());
        row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
        row.getCell(6).setCellValue(businessData.getNewUsers());
        row = sheet.getRow(4);
        row.getCell(2).setCellValue(businessData.getValidOrderCount());
        row.getCell(4).setCellValue(businessData.getUnitPrice());
        for (int i = 0; i < 30; i++) {
            LocalDate date = begin.plusDays(i);
            //准备明细数据
            businessData = workspaceService.getBusinessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
            row = sheet.getRow(7 + i);
            row.getCell(1).setCellValue(date.toString());
            row.getCell(2).setCellValue(businessData.getTurnover());
            row.getCell(3).setCellValue(businessData.getValidOrderCount());
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            row.getCell(5).setCellValue(businessData.getUnitPrice());
            row.getCell(6).setCellValue(businessData.getNewUsers());
        }
        //通过输出流将文件下载到客户端浏览器中
        ServletOutputStream out = response.getOutputStream();
        excel.write(out);
        //关闭资源
        out.flush();
        out.close();
        excel.close();
    }
}
