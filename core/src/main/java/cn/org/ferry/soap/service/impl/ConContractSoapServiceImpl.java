package cn.org.ferry.soap.service.impl;

import cn.org.ferry.soap.dto.ConContract;
import cn.org.ferry.soap.dto.ConContractBase;
import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;
import cn.org.ferry.soap.service.ConContractSoapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>基于 soap 协议的起租信息同步业务实现
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/05 11:44
 */

@Service
public class ConContractSoapServiceImpl implements ConContractSoapService {
    private static final Logger logger = LoggerFactory.getLogger(ConContractSoapServiceImpl.class);

    @Override
    public OutHeaderMessage<ConContract> contractIncept(InHeaderMessage inHeaderMessage, ConContractBase conContractBase) {
        logger.info(inHeaderMessage.toString());
        logger.info(conContractBase.toString());
        return null;
    }
}
