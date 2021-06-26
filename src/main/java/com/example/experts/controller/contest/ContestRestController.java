package com.example.experts.controller.contest;

import com.example.experts.controller.abstraction.BaseCRUDController;
import com.example.experts.dto.contest.ContestDTO;
import com.example.experts.entity.contest.Contest;
import com.example.experts.repository.contest.ContestRepository;
import com.example.experts.service.contest.ContestReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

/**
 * Контроллер запросов для работы с конкурсами
 */
@RestController
@RequestMapping("/api/contest")
@RequiredArgsConstructor
public class ContestRestController extends BaseCRUDController<Contest, ContestDTO> {
    private final ContestRepository contestRepository;
    private final ContestReportService contestReportService;

    /**
     * Обработка запроса получения отчета
     *
     * @param id идентификатор отчета
     * @return json ответ
     */
    @RequestMapping(value = "/{id}/report", method = RequestMethod.GET)
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        return contestRepository.findByIdAndDeletedFalse(id).map(contest -> {
            try {
                return ResponseEntity.ok()
                        .header("Content-Type", "application/pdf; charset=UTF-8")
                        .header("Content-Disposition", "inline; filename=\"contest_" + id + ".pdf\"")
                        .body(contestReportService.exportReport(contest));
            } catch (JRException | FileNotFoundException e) {
                e.printStackTrace();
                return ResponseEntity.noContent().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
