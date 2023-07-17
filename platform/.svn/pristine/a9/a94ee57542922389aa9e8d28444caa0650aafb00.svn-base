package com.bsoft.quartz.service.impl;

import com.bsoft.common.annotation.DataSource;
import com.bsoft.common.enums.DataSourceType;
import com.bsoft.quartz.mapper.MyTaskMapper;
import com.bsoft.quartz.service.MyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements MyTaskService {

    @Autowired
    private MyTaskMapper myTaskMapper;

    @Override
    @DataSource(value = DataSourceType.THREE)
    public int saveClrcjbxx(List<Map<String, Object>> list) {
        int i=0;
        // 去重
        list = list.stream().distinct().collect(Collectors.toList());
        // 查询出已存在是的身份证信息
        List<Map<String,Object>> record = myTaskMapper.selectBySfzh(list);
        if(!ObjectUtils.isEmpty(record)){
            // 过滤已存在的身份证信息
            for(Map<String,Object> map : record){
                list = list.stream().filter(item->!map.get("sfzh").equals(item.get("sfzh"))).collect(Collectors.toList());
            }
        }
        if(!ObjectUtils.isEmpty(list)){
            // 保存过滤后剩余身份证信息
            i = myTaskMapper.insertClrcjbxx(list);
        }

        return  i;
    }
}
