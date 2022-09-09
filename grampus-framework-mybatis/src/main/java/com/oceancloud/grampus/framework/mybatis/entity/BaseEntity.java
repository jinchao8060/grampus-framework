package com.oceancloud.grampus.framework.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 基础实体类，所有实体都需要继承
 * Project: grampus
 *
 * @author Beck
 * @since 2020-12-02
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = -1799573625766414211L;
	/**
	 * id
	 */
	@TableId
	private Long id;
}
