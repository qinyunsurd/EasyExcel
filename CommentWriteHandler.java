package com.imydao.security.excel;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

/**
 * @author admin
 * @date CommentWriteHandler
 */
public class CommentWriteHandler extends AbstractRowWriteHandler {

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            Sheet sheet = writeSheetHolder.getSheet();
            Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
            // 在第一行 第二列创建一个批注
            Comment comment = drawingPatriarch.createCellComment(
                    new XSSFClientAnchor(0, 0, 0, 0, (short) 8, 0, (short) 8, 0));
            // 输入批注信息
            comment.setString(new XSSFRichTextString("时间格式: YYYY-MM-DD"));
            // 将批注添加到单元格对象中
            sheet.getRow(0).getCell(8).setCellComment(comment);
        }
    }
}
