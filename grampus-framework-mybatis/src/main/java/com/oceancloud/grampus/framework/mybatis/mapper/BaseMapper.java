package com.oceancloud.grampus.framework.mybatis.mapper;

/**
 * CRUD基类(Mapper继承该接口后，无需编写mapper.xml文件，即可获得CRUD功能)
 * Project: grampus
 *
 * @author Beck
 * @since 2022-04-11
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

}
