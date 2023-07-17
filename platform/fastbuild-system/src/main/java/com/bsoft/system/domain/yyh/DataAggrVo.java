package com.bsoft.system.domain.yyh;

import lombok.Data;

import java.util.List;

/**
 * 医养护数据归集所需参数
 */
@Data//提供getter setter
public class DataAggrVo {
	private YYH_CLIENT client;
	private List<YYH_DATA_AGGR> dataAggrs;
	private String begindate;
	private String enddate;
}
