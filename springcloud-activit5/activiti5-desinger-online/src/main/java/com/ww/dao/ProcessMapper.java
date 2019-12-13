package com.ww.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProcessMapper {

    List<Map<String, Object>> selectModels();

    List<Map<String, Object>> selectProcess();

}