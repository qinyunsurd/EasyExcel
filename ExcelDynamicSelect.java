package com.imydao.security.excel;

import java.util.Map;

/**
 * @author admin
 * @date ExcelDynamicSelect
 */
public interface ExcelDynamicSelect {
    /**
     * 获取动态生成的下拉框可选数据
     * @return 动态生成的下拉框可选数据
     */
    String[] getSource(Map<String,String> map);
}
