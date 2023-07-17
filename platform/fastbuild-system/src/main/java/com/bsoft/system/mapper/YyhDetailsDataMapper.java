package com.bsoft.system.mapper;

import com.bsoft.system.domain.yyh.Entity.*;

import java.util.List;
import java.util.Map;

public interface YyhDetailsDataMapper {


    public List<TjBgjl> getTjBgjlData(Map<String,Object> params);

    public  List<TjCgjc> getTjCgjcData(Map<String,Object> params);

    public  List<TjFzjc> getTjFzjcData(Map<String,Object> params);

    public  List<TjSysjy> getTjSysjyData(Map<String,Object> params);

    public  List<TjSysjycgbg> getTjSysjycgbgData(Map<String,Object> params);

    public  List<TjYczyxx> getTjYczyxxData(Map<String,Object> params);

}
