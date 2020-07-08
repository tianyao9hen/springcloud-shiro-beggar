package com.study.cloud.mapper;

import com.study.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * LoginMapper
 *
 * @author pxf
 * @version v1.0
 * @Date 2020-07-06
 */
@Mapper
public interface LoginMapper {

    UserEntity queryUserByLoginName(@Param("loginName") String loginName);
}