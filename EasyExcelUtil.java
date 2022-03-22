package com.imydao.security.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @date EasyExcelUtil
 */
@Slf4j
public class EasyExcelUtil {
    /**
     * 创建即将导出的sheet页（sheet页中含有带下拉框的列）
     * @param head 导出的表头信息和配置
     * @param sheetNo sheet索引
     * @param sheetName sheet名称
     * @param <T> 泛型
     * @return sheet页
     */
    public static <T> WriteSheet writeSelectedSheet(Class<T> head, Integer sheetNo, String sheetName,Map<String,String> map) {
        Map<Integer, ExcelSelectedResolve> selectedMap = resolveSelectedAnnotation(head,map);

        return EasyExcel.writerSheet(sheetNo, sheetName)
                .head(head)
                .registerWriteHandler(new CommentWriteHandler())
                .registerWriteHandler(new CustomColumnWidthStyle())
                .registerWriteHandler(new CustomSheetWriteHandler(selectedMap))
                .build();
    }
    /**
     * 解析表头类中的下拉注解
     * @param head 表头类
     * @param <T> 泛型
     * @return Map<下拉框列索引, 下拉框内容> map
     */
    private static <T> Map<Integer, ExcelSelectedResolve> resolveSelectedAnnotation(Class<T> head,Map<String,String> map) {
        Map<Integer, ExcelSelectedResolve> selectedMap = new HashMap<>();

        // getDeclaredFields(): 返回全部声明的属性；getFields(): 返回public类型的属性
        Field[] fields = head.getDeclaredFields();
        for (int i = 0; i < fields.length; i++){
            Field field = fields[i];
            // 解析注解信息
            ExcelSelected selected = field.getAnnotation(ExcelSelected.class);
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            if (selected != null) {
                ExcelSelectedResolve excelSelectedResolve = new ExcelSelectedResolve();
                String[] source = excelSelectedResolve.resolveSelectedSource(selected,map);
                if (source != null && source.length > 0){
                    excelSelectedResolve.setSource(source);
                    excelSelectedResolve.setFirstRow(selected.firstRow());
                    excelSelectedResolve.setLastRow(selected.lastRow());
                    if (property != null && property.index() >= 0){
                        selectedMap.put(property.index(), excelSelectedResolve);
                    } else {
                        selectedMap.put(i, excelSelectedResolve);
                    }
                }
            }
        }

        return selectedMap;
    }
}
