package com.imydao.security.excel;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.CollectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @date CustomColumnWidthStyle
 */
public class CustomColumnWidthStyle extends AbstractColumnWidthStyleStrategy {
    private static int MAX_COLUMN_WIDTH = 255;
    private Map<Integer,Map<Integer,Integer>> cache = new HashMap<>(8);

    public CustomColumnWidthStyle() {
    }

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth){
            Map<Integer, Integer> maxColumnWidthMap = cache.get(writeSheetHolder.getSheetNo());
            if (null == maxColumnWidthMap){
                maxColumnWidthMap = new HashMap<>(16);
                cache.put(writeSheetHolder.getSheetNo(),maxColumnWidthMap);
            }

            Integer columnWidth = this.dataLength(cellDataList,cell,isHead);
            if (0 <= columnWidth){
                if (MAX_COLUMN_WIDTH < columnWidth){
                    columnWidth = MAX_COLUMN_WIDTH;
                }
                Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                if (null == maxColumnWidth || columnWidth > maxColumnWidth){
                    maxColumnWidthMap.put(cell.getColumnIndex(),columnWidth);
                    writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(),7250);
                }
            }
        }
    }

    private Integer dataLength(List<CellData> cellDataList, Cell cell, Boolean isHead) {
        if (isHead){
            return cell.getStringCellValue().getBytes().length;
        } else {
         CellData cellData = cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (null == type){
                return -1;
            } else {
                switch (type){
                    case STRING:
                        return cellData.getStringValue().getBytes().length;
                    case BOOLEAN:
                        return cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER:
                        return cellData.getNumberValue().toString().getBytes().length;
                    default:
                        return -1;
                }
            }
        }
    }
}
