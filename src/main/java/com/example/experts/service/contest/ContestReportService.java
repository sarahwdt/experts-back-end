package com.example.experts.service.contest;

import com.example.experts.entity.contest.Contest;
import com.example.experts.mapper.contest.ContestMapper;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;

/**
 * Класс, отвечающий за генерацию отчетов
 */
@Service
@RequiredArgsConstructor
public class ContestReportService {
    private final ContestMapper contestMapper;

    /**
     * Метод генерации отчета для конкурса
     * @param contest идентификатор конкурса
     * @return byte-file
     */
    public byte[] exportReport(Contest contest) throws JRException, FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:ContestReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(),
                new JRBeanCollectionDataSource(Collections.singleton(contestMapper.toDto(contest))));
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/nastya/Desktop/Новая папка/contest.pdf");
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
