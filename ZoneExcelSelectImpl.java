package com.imydao.security.excel;

import com.imydao.security.service.SecurityPersonService;
import com.imydao.util.SpringContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 * @author admin
 * @date EduExcelSelectImpl
 * 作业区
 */

public class ZoneExcelSelectImpl implements ExcelDynamicSelect{

    @Override
    public String[] getSource(Map<String,String> map) {
        SecurityPersonService bean = SpringContextUtil.getBean(SecurityPersonService.class);
        Map<String, String> stringStringMap = bean.mapCustomerDict(map.get("clientId"));
        Collection<String> values = stringStringMap.values();
        return values.toArray(new String[values.size()]);
    }
}
