package com.example.experts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Метод определяющий работу итеративнорго алгоритма
 */
@Service
@RequiredArgsConstructor
public class IterativeService {

    /**
     * Итерационный процесс
     * @param matrix матрица
     * @return относительные показатели оценки
     */
    public List<Float> iterationProcess(List<List<Float>> matrix) {
        float matrixSum = matrixSum(matrix);
        List<Float> result = matrixSumByRow(matrix);
        List<Float> relation = relationEvaluate(matrixSum, result);
        List<Float> relationOld;
        List<List<Float>> matrixCurrent = new ArrayList<>(matrix);
        List<List<Float>> matrixOld;
        do {
            relationOld = new ArrayList<>(relation);
            matrixOld = new ArrayList<>(matrixCurrent);
            matrixCurrent = new ArrayList<>();
            for (int i = 0; i < matrixOld.size(); i++) {
                matrixCurrent.add(new ArrayList<>());
                for (int j = 0; j < matrixOld.get(i).size(); j++)
                    matrixCurrent.get(i).add(rowColumnMultiple(matrixOld.get(i), relationOld));
            }
            matrixSum = matrixSum(matrixCurrent);
            result = matrixSumByRow(matrixCurrent);
            relation = relationEvaluate(matrixSum, result);
        } while (needNext(relationOld, relation));

        return relation;
    }

    /**
     * Расчет суммы всех элементов матрицы
     * @param matrix матрица
     * @return сумма всех элементов
     */
    private float matrixSum(List<List<Float>> matrix) {
        return matrix.stream().flatMap(Collection::stream).reduce(0f, Float::sum);
    }

    /**
     * Расчет суммы по строке
     * @param matrix матрица
     * @return сумма по строкам
     */
    private List<Float> matrixSumByRow(List<List<Float>> matrix) {
        return matrix.stream().map(floats -> floats.stream().reduce(0f, Float::sum))
                .collect(Collectors.toList());
    }

    /**
     * Расчет относительной оценки
     * @param sum сумма общая
     * @param sumByRows сумма по строкам
     * @return относительная оценка
     */
    private List<Float> relationEvaluate(float sum, List<Float> sumByRows) {
        return sumByRows.stream().map(sumByRow -> sumByRow / sum).collect(Collectors.toList());
    }

    /**
     * Перемножение строки и столбца
     * @param row строка
     * @param column столбец
     * @return число
     */
    private float rowColumnMultiple(List<Float> row, List<Float> column) {
        float result = 0f;
        for (int i = 0; i < row.size(); i++)
            result += row.get(i) * column.get(i);
        return result;
    }

    /**
     * Условие остановки
     * @param old старые значения
     * @param current новые значения
     * @return нужна следующая итерация или нет
     */
    private boolean needNext(List<Float> old, List<Float> current) {
        for (int i = 0; i < old.size(); i++)
            if (Math.abs(old.get(i) - current.get(i)) > current.get(i) * 0.01)
                return true;
        return false;
    }
}
