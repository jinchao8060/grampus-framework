package com.oceancloud.grampus.framework.core.utils.tree;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树节点，所有需要实现树节点的，都需要继承该类
 *
 * @author Beck
 * @since 2021-01-23
 */
@Getter
@Setter
public class TreeNode<T> implements Serializable {
	private static final long serialVersionUID = -8758990539127498041L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 上级ID
	 */
	private Long parentId;
	/**
	 * 子节点列表
	 */
	private List<T> children = new ArrayList<>();
}