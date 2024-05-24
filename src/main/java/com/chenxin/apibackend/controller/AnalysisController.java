package com.chenxin.apibackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenxin.apibackend.annotation.AuthCheck;
import com.chenxin.apibackend.common.BaseResponse;
import com.chenxin.apibackend.common.ErrorCode;
import com.chenxin.apibackend.common.ResultUtils;
import com.chenxin.apibackend.constant.UserConstant;
import com.chenxin.apibackend.exception.BusinessException;
import com.chenxin.apibackend.mapper.UserInterfaceInfoMapper;
import com.chenxin.apibackend.model.vo.InterfaceInfoRankVO;
import com.chenxin.apibackend.service.InterfaceInfoService;
import com.chenxin.apibackend.service.UserInterfaceInfoService;
import com.chenxin.apicommon.model.entity.InterfaceInfo;
import com.chenxin.apicommon.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/23 23:50
 * @modify
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<InterfaceInfoRankVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        if (CollectionUtils.isEmpty(userInterfaceInfoList)) {
            return ResultUtils.success(new ArrayList<>());
        }
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        if (CollectionUtils.isEmpty(interfaceInfoList)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(interfaceInfoList.stream().map(interfaceInfo -> {
            InterfaceInfoRankVO interfaceInfoRankVO = new InterfaceInfoRankVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoRankVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoRankVO.setTotalNum(totalNum);
            return interfaceInfoRankVO;
        }).collect(Collectors.toList()));
    }



}
