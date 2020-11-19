package com.sfac.javaSpringBoot.modules.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.account.entity.Resource;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;

public interface ResourceService {

    Result<Resource> editResource(Resource resource);

    Result<Resource> deleteResource(int resourceId);

    PageInfo<Resource> getResources(SearchVo searchVo);

    List<Resource> getResourcesByRoleId(int roleId);

    Resource getResourceById(int resourceId);
}