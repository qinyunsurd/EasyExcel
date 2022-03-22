package com.imydao.security.excel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author admin
 * @date ExcelSelectedResolve
 * excel 选择框解析类
 */
@Data
@Slf4j
public class ExcelSelectedResolve {
    /**
     * 下拉内容
     */
    private String[] source;

    /**
     * 设置下拉框的起始行，默认为第二行
     */
    private int firstRow;

    /**
     * 设置下拉框的结束行，默认为最后一行
     */
    private int lastRow;
    public String[] resolveSelectedSource(ExcelSelected excelSelected, Map<String,String> map) {
        if (excelSelected == null) {
            return null;
        }

        // 获取固定下拉框的内容
        String[] source = excelSelected.source();
        if (source.length > 0) {
            return source;
        }

        // 获取动态下拉框的内容
        Class<? extends ExcelDynamicSelect>[] classes = excelSelected.sourceClass();
        if (classes.length > 0) {
            try {
                ExcelDynamicSelect excelDynamicSelect = classes[0].newInstance();
                String[] dynamicSelectSource = excelDynamicSelect.getSource(map);
                if (dynamicSelectSource != null && dynamicSelectSource.length > 0) {
                    return dynamicSelectSource;
                }
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("解析动态下拉框数据异常", e);
            }
        }
        return null;
    }
}
