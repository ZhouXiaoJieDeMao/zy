

package com.bsoft.web.controller.yyh;

import com.bsoft.common.constant.HttpStatus;
import com.bsoft.common.core.controller.BaseController;
import com.bsoft.common.core.domain.AjaxResult;
import com.bsoft.common.core.page.TableDataInfo;
import com.bsoft.system.domain.drgs.DrgsRequestBody;
import com.bsoft.system.service.IYyhDataAggrService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huan.gao
 * @date 2019/2/1
 */
@RestController
@RequestMapping("/yyh")
public class YyhDataAggrController extends BaseController {

	@Autowired
	private IYyhDataAggrService iYyhDataAggrService;

    /**
     * 体检pdf上传分页列表
     * @param body
     * @return
     */
    @PostMapping(value = {"/tJUploadQueryPage"})
    public TableDataInfo tjPDFUploadQueryPage(@RequestBody DrgsRequestBody body) {
        return getDataTable(iYyhDataAggrService.tJUploadQueryPage(body));
    }

    /**
     * 体检记录上传分页列表
     * @param body
     * @return
     */
    @PostMapping(value = {"/tjjlUploadQueryPage"})
    public TableDataInfo tjjlUploadQueryPage(@RequestBody DrgsRequestBody body) {
//        return getDataTable(iYyhDataAggrService.tjjlUploadQueryPage(body));
        PageInfo pageInfo = iYyhDataAggrService.tjjlUploadQueryPage(body);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(pageInfo.getList());
        rspData.setTotal(pageInfo.getTotal());
        return rspData;
    }

    /**
     * 体检PDF上传方法
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = {"/tJPdfUpload"})
    public AjaxResult tJPdfUpload(@RequestBody Map params) throws Exception {
        return AjaxResult.success(iYyhDataAggrService.tJPdfUpload(params));
    }

    /**
     * 体检记录上传方法
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = {"/tJjlUpload"})
    public AjaxResult tJjlUpload(@RequestBody Map<String,Object> params) throws Exception {
        return AjaxResult.success(iYyhDataAggrService.tJjlUpload(params));
    }

    /**
     * 上传记录作废
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = {"/discard"})
    public AjaxResult discard(@RequestBody Map<String,Object> params) throws Exception {
        return AjaxResult.success(iYyhDataAggrService.discard(params));
    }

    /***
     * 体检明细数据查询
     * @param params
     * @return
     */
    @PostMapping(value = {"/detailsData"})
    public AjaxResult detailsData(@RequestBody Map<String,Object> tjdata)  {

         String[] data = tjdata.get("tjbh").toString().split("@");

         Map<String,Object> params = new HashMap<>();


        params.put("jgdm",data[0]);
        params.put("bgdbh",data[2]);



        return AjaxResult.success(iYyhDataAggrService.detailsData(params));
    }

}
