package cn.org.ferry.soap.service.impl;

import cn.org.ferry.soap.dto.ChineseQueryBody;
import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;
import cn.org.ferry.soap.service.ChinesePeopleSoapService;
import cn.org.ferry.soap.service.SoapWebServiceCommon;
import cn.org.ferry.sys.dto.ChinesePeople;
import cn.org.ferry.sys.service.ChinesePeopleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>基于 soap 协议的人员接口实现类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/25 17:08
 */
@Service
public class ChinesePeopleSoapServiceImpl implements ChinesePeopleSoapService {
    private static final Logger logger = LoggerFactory.getLogger(ChinesePeopleSoapServiceImpl.class);

    @Autowired
    private ChinesePeopleService chinesePeopleService;

    @Override
    public OutHeaderMessage<ChinesePeople> query(InHeaderMessage inHeaderMessage, ChineseQueryBody chinessQueryBody) {
        logger.info("inHeaderMessage: {}", inHeaderMessage);
        logger.info("chineseQueryBody: {}", chinessQueryBody);

        OutHeaderMessage<ChinesePeople> outHeaderMessage =  new OutHeaderMessage<>();
        outHeaderMessage.setServerName("chineseQuery");
        outHeaderMessage.setStatus(SoapWebServiceCommon.Status.SUCCESS.name());
        outHeaderMessage.setList(chinesePeopleService.queryByName(chinessQueryBody.getName(), 0, 0));
        logger.info("outHeaderMessage : {}", outHeaderMessage);
        return outHeaderMessage;
    }
}