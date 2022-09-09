package com.oceancloud.grampus.framework.core.utils.tree;

import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Tree构建工具
 *
 * @author Beck
 * @since 2021-03-31
 */
@UtilityClass
public class TreeUtil {

	/**
	 * 构建结点树
	 *
	 * @param treeNodeList 树结点列表
	 * @param rootId       根结点ID(作为id不一定需要存在，但是作为parentId必须存在)
	 */
	public static <T extends TreeNode> List<T> buildTree(List<T> treeNodeList, Long rootId) {
		Map<Long, List<T>> pidMap = treeNodeList.stream().collect(Collectors.groupingBy(T::getParentId));
		List<T> firstFloors = pidMap.get(rootId);
		for (T child : firstFloors) {
			assembleTree(pidMap, child);
		}
		return firstFloors;
	}

	/**
	 * 组装一棵树
	 *
	 * @param rootNode 需要补全的树的根结点
	 * @return 补全后的根结点
	 */
	public static <T extends TreeNode> T assembleTree(Map<Long, List<T>> pidMap, T rootNode) {
		List<T> childList = pidMap.get(rootNode.getId());
		if (!CollectionUtils.isEmpty(childList)) {
			for (T child : childList) {
				rootNode.getChildren().add(assembleTree(pidMap, child));
			}
		}
		return rootNode;
	}

	/**
	 * 构建树节点
	 */
	public static <T extends TreeNode> List<T> build(List<T> treeNodes) {
		// list转map
		Map<Long, T> nodeMap = treeNodes.stream()
				.collect(Collectors.toMap(T::getId, Function.identity(), (oldValue, newValue) -> newValue, LinkedHashMap::new));
		List<T> result = new ArrayList<>(nodeMap.size());
		for (T node : nodeMap.values()) {
			T parent = nodeMap.get(node.getParentId());
			if (parent != null && !(node.getId().equals(parent.getId()))) {
				parent.getChildren().add(node);
				continue;
			}
			result.add(node);
		}
		return result;
	}
}