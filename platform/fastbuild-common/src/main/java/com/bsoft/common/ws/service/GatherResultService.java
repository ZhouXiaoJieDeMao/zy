//package com.bsoft.common.ws.service;
//
//import cn.com.bsoft.hcn.entity.GatherFeedback;
//import cn.com.bsoft.hcn.entity.Task;
//import cn.com.bsoft.hcn.mapper.pg.GatherFeedbackMapper;
//import cn.com.bsoft.hcn.mapper.pg.TaskDao;
//import cn.com.bsoft.hcn.ws.model.basic.ReturnMessage;
//import cn.com.bsoft.hcn.ws.model.basic.Visitor;
//import cn.com.bsoft.hcn.ws.model.request.reception.ReceptionResult;
//import cn.com.bsoft.hcn.ws.model.request.reception.Result;
//import cn.com.bsoft.hcn.ws.model.request.reception.TableInfo;
//import cn.com.bsoft.hcn.ws.model.response.reception.ReceptionResponse;
//import cn.com.bsoft.hcn.ws.utils.DateUtil;
//import cn.com.bsoft.hcn.ws.utils.XmlBeanUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class GatherResultService {
//
//    @Autowired
//    GatherFeedbackMapper gatherFeedbackMapper;
//    @Autowired
//    TaskDao taskDao;
//
//    private Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
//
//    public String reception(String params) {
//        ReceptionResult result = XmlBeanUtil.converyToJavaBean(params, ReceptionResult.class);
//        ReceptionResponse receptionResponse = new ReceptionResponse();
//        String errorMessage = "";
//
//        GatherFeedback gatherFeedback = new GatherFeedback();
//
//        Visitor visitor = result.getReqSwitchSet().getVisitor();
//        gatherFeedback.setOrganCode(visitor.getSourceOrgan());
//
//        Result result1 = result.getReceptionBusiness().getReceptionBusinessData().getResult();
//        gatherFeedback.setTaskNo(result1.getTaskId());
//        gatherFeedback.setSystemCode(result1.getDomainCode());
//        gatherFeedback.setSystemName(result1.getDomainName());
//        gatherFeedback.setResourceCode(result1.getResourceCode());
//        gatherFeedback.setResourceName(result1.getResourceName());
//        try {
//            gatherFeedback.setGatherDate(DateUtil.paseDate(result1.getUploadTime()));
//        } catch (Exception e) {
//            e.printStackTrace();
//
////            GatherFeedback error = new GatherFeedback();
//            gatherFeedback.setMessage(params);
//            gatherFeedbackMapper.add(gatherFeedback);
//
//            errorMessage = "数据采集接收日期格式有误！";
//            ReturnMessage returnMessage = receptionResponse.getReceptionBusiness().getReturnMessage();
//            returnMessage.setRetCode("0");
//            returnMessage.setRetText(errorMessage);
//            receptionResponse.getExtendSet().setToken("123");
//            String resXml = XmlBeanUtil.convertToXml(receptionResponse);
//            return resXml;
//        }
//
//        gatherFeedback.setResult(result1.getResultCode().toString());
//
//        TableInfo tableInfo = result1.getTables().getTableInfo()[0];
//        gatherFeedback.setRelationTable(tableInfo.getTableName());
//        gatherFeedback.setRelationTable_des(tableInfo.getTableDesc());
//        gatherFeedback.setDatasetCode(tableInfo.getSetCode());
//        gatherFeedback.setDatasetName(tableInfo.getSetName());
//        gatherFeedback.setTotalRecordNum(tableInfo.getRecordCount());
//        gatherFeedback.setErrorRecordNum(tableInfo.getErrorRecordCount());
//        gatherFeedback.setRuleNum(tableInfo.getRuleCount());
//        gatherFeedback.setErrorRuleNum(tableInfo.getErrorCount());
//
//        gatherFeedback.setReport(result1.getReport());
//        gatherFeedback.setMessage(params);
//        gatherFeedback.setReceptionDate(new Date());
//
//        gatherFeedbackMapper.add(gatherFeedback);
//
//        Task task = taskDao.getTaskByTaskNo(result1.getTaskId());
//        if (task != null) {
//            task.setGatherId(gatherFeedback.getId());
//            if ("1".equals(gatherFeedback.getResult()) && 0 == gatherFeedback.getErrorRecordNum()) {
//                task.setStatus(Task.CHECK_SUCCESS);
//            } else {
//                task.setStatus(Task.CHECK_FAILURE);
//            }
//            taskDao.upBack(task);
//        }
//
//        ReturnMessage returnMessage = receptionResponse.getReceptionBusiness().getReturnMessage();
//        returnMessage.setRetCode("1");
//        returnMessage.setRetText("接收成功");
//        receptionResponse.getExtendSet().setToken("123");
//        String resXml = XmlBeanUtil.convertToXmlS(receptionResponse);
//        logger.info(tableInfo.getSetName() + " 校验接收成功");
//        return resXml;
//    }
//}
