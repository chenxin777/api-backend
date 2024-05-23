package com.chenxin.apibackend.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenxin.apibackend.common.ErrorCode;
import com.chenxin.apibackend.exception.BusinessException;
import com.chenxin.apibackend.mapper.InterfaceInfoMapper;
import com.chenxin.apibackend.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.chenxin.apibackend.model.vo.InterfaceInfoVO;
import com.chenxin.apibackend.service.InterfaceInfoService;
import com.chenxin.apicommon.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author fangchenxin
 * @description 针对表【interface_info(api.`interface_info`)】的数据库操作Service实现
 * @createDate 2024-05-16 12:08:51
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Override
    public void validInterface(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        if (StringUtils.isBlank(name) || name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称不符合要求");
        }
        String description = interfaceInfo.getDescription();
        if (description.length() > 300) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口描述过长");
        }
        String url = interfaceInfo.getUrl();
        if (StringUtils.isBlank(url)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口地址为空");
        }
        String requestHeader = interfaceInfo.getRequestHeader();
        if (StringUtils.isBlank(requestHeader)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求头为空");
        }
        String responseHeader = interfaceInfo.getResponseHeader();
        if (StringUtils.isBlank(responseHeader)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "响应头为空");
        }
        String method = interfaceInfo.getMethod();
        if (StringUtils.isBlank(method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求方法为空");
        }
    }

    @Override
    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (interfaceInfoQueryRequest == null) {
            return queryWrapper;
        }
        return queryWrapper;
    }

    @Override
    public InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request) {
        InterfaceInfoVO interfaceInfoVO = InterfaceInfoVO.objToVo(interfaceInfo);
        return interfaceInfoVO;
    }

    @Override
    public Page<InterfaceInfoVO> getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request) {
        List<InterfaceInfo> postList = interfaceInfoPage.getRecords();
        Page<InterfaceInfoVO> interfaceInfoVOPage = new Page<>(interfaceInfoPage.getCurrent(), interfaceInfoPage.getSize(), interfaceInfoPage.getTotal());
        if (CollUtil.isEmpty(postList)) {
            return interfaceInfoVOPage;
        }
        return interfaceInfoVOPage;
    }
}




