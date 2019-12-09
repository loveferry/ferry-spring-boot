package cn.org.ferry.soap.service.impl;

import cn.org.ferry.soap.dto.ConContract;
import cn.org.ferry.soap.dto.ConContractBase;
import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;
import cn.org.ferry.soap.service.ConContractSoapService;
import org.springframework.stereotype.Service;

/**
 * <p>基于 soap 协议的起租信息同步业务实现
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/05 11:44
 */

@Service
public class ConContractSoapServiceImpl implements ConContractSoapService {

    @Override
    public OutHeaderMessage<ConContract> contractIncept(InHeaderMessage inHeaderMessage, ConContractBase conContractBase) {
        return null;
    }
}
