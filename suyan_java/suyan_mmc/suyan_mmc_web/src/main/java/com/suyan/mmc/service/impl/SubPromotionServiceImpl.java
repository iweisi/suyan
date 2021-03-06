package com.suyan.mmc.service.impl;

import com.suyan.mmc.biz.SubPromotionBiz;
import com.suyan.mmc.convertor.SubPromotionConvertor;
import com.suyan.mmc.model.SubPromotion;
import com.suyan.mmc.req.SubPromotionDTO;
import com.suyan.mmc.req.SubPromotionQueryDTO;
import com.suyan.mmc.resp.SubPromotionODTO;
import com.suyan.mmc.resp.base.QueryResultODTO;
import com.suyan.mmc.result.MmcResult;
import com.suyan.mmc.service.ISubPromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.suyan.mmc.validate.SubPromotionValidate.validateForCreate;
import static com.suyan.mmc.validate.SubPromotionValidate.validateForUpdate;

/**
 * @CopyRright (c)2008-2017: <素焉信息技术有限公司>
 * @Project: <mmc>
 * @Comments: <Dubbo Service 订单促销管理类>
 * @jdk 1.8
 * @Author: <lixavier>
 * @email : <lixavier@163.com>
 * @Create Date: <2018-10-18>
 * @Modify Date: <2018-10-18>
 * @Version: <1.0>
 */
@Service("subPromotionService")
public class SubPromotionServiceImpl implements ISubPromotionService {
    private final Logger logger = LoggerFactory.getLogger(SubPromotionServiceImpl.class);

    @Autowired
    private SubPromotionBiz subPromotionBiz;

    /**
     * 删除订单促销
     *
     * @param id
     * @param updateUser
     * @param updateUserName
     * @return
     * @Author: <lixavier@163.com>
     * @Version: <1.0>
     */
    @Override
    public MmcResult<Integer> deleteSubPromotion(Long id, String updateUser, String updateUserName) {
        MmcResult<Integer> result = MmcResult.newSuccess();
        result.setDataMap(subPromotionBiz.deleteSubPromotion(id, updateUser, updateUserName));
        return result;
    }

    /**
     * 创建订单促销
     *
     * @param subPromotionDTO
     * @return
     * @Author: <lixavier@163.com>
     * @Version: <1.0>
     */
    @Override
    public MmcResult<Long> createSubPromotion(SubPromotionDTO subPromotionDTO) {
        MmcResult<Long> result = MmcResult.newSuccess();
        SubPromotion subPromotion = SubPromotionConvertor.toSubPromotion(subPromotionDTO);
        if (!validateForCreate(subPromotion, result)) {
            return result;
        }
        result.setDataMap(subPromotionBiz.createSubPromotion(subPromotion));
        return result;
    }

    @Override
    public MmcResult<Integer> batchCreate(List<SubPromotionDTO> subPromotionDTOs) {
        MmcResult<Integer> result = MmcResult.newSuccess();
        List<SubPromotion> items = SubPromotionConvertor.toSubPromotionList(subPromotionDTOs);
        result.setDataMap(subPromotionBiz.batchCreateSubPromotion(items));
        return result;
    }

    /**
     * 更新订单促销
     *
     * @param subPromotionDTO
     * @return
     * @Author: <lixavier@163.com>
     * @Version: <1.0>
     */
    @Override
    public MmcResult<Integer> updateSubPromotion(SubPromotionDTO subPromotionDTO) {
        MmcResult<Integer> result = MmcResult.newSuccess();
        SubPromotion subPromotion = SubPromotionConvertor.toSubPromotion(subPromotionDTO);
        if (!validateForUpdate(subPromotion, result)) {
            return result;
        }
        result.setDataMap(subPromotionBiz.updateSubPromotion(subPromotion));
        return result;
    }

    /**
     * 根据ID获取订单促销信息
     *
     * @param id
     * @return
     * @Author: <lixavier@163.com>
     * @Version: <1.0>
     */
    @Override
    public MmcResult<SubPromotionODTO> getSubPromotion(Long id) {
        MmcResult<SubPromotionODTO> result = MmcResult.newSuccess();
        SubPromotion subPromotion = subPromotionBiz.getSubPromotion(id);
        SubPromotionODTO subPromotionODTO = SubPromotionConvertor.toSubPromotionODTO(subPromotion);
        result.setDataMap(subPromotionODTO);
        return result;
    }

    /**
     * 分页查询订单促销信息
     *
     * @param subPromotionQueryDTO
     * @return
     * @Author: <lixavier@163.com>
     * @Version: <1.0>
     */
    @Override
    public MmcResult<QueryResultODTO<SubPromotionODTO>> querySubPromotion(SubPromotionQueryDTO subPromotionQueryDTO) {
        MmcResult<QueryResultODTO<SubPromotionODTO>> result = MmcResult.newSuccess();

        QueryResultODTO<SubPromotion> resultInfo = subPromotionBiz.querySubPromotion(subPromotionQueryDTO);
        result.setDataMap(SubPromotionConvertor.toQueryResult(resultInfo));

        return result;
    }

    @Override
    public MmcResult<Integer> changeStatus(SubPromotionDTO subPromotionDTO) {
        MmcResult<Integer> result = MmcResult.newSuccess();
        SubPromotion subPromotion = SubPromotionConvertor.toSubPromotion(subPromotionDTO);
        result.setDataMap(subPromotionBiz.changeStatus(subPromotion));
        return result;
    }

}