package com.sky.service;

import com.sky.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    UserReportVO userStatistics(LocalDate begin, LocalDate end);

    OrderReportVO orderStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO Top10Statistics(LocalDate begin, LocalDate end);

    void exportBusinessData(HttpServletResponse response) throws IOException;
}
