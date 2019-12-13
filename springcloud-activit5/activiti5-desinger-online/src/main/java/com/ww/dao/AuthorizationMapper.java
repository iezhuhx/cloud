package com.ww.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorizationMapper {

    List<String> selectRoleIdsByUserId(String userId);
}