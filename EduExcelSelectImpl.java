package com.imydao.security.excel;

import com.imydao.security.service.SecurityPersonService;
import com.imydao.util.SpringContextUtil;
import java.util.Collection;
import java.util.Map;

/**
 * @author admin
 * @date EduExcelSelectImpl
 * 学历
 */
public class EduExcelSelectImpl implements ExcelDynamicSelect{
    @Override
    public String[] getSource(Map<String,String> map) {
        SecurityPersonService bean = SpringContextUtil.getBean(SecurityPersonService.class);
        Map<String, String> stringStringMap = bean.mapDict();
        Collection<String> values = stringStringMap.values();
        return values.toArray(new String[values.size()]);
    }
}
