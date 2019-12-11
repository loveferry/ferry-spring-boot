package cn.org.ferry.soap.service.impl;

import cn.org.ferry.soap.dto.ContractChange;
import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;
import cn.org.ferry.soap.service.ContractChangeSoapService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/11 11:52
 */

@Service
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public class ContractChangeSoapServiceImpl implements ContractChangeSoapService {

    @Override
    public OutHeaderMessage<ContractChange> contractChange(InHeaderMessage inHeaderMessage, List<ContractChange> contractChangeList) {
        return null;
    }
}
