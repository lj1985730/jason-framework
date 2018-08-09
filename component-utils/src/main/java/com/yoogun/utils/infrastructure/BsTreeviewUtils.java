package com.yoogun.utils.infrastructure;

import com.yoogun.core.domain.model.TreeEntity;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.application.dto.BsTreeview;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bootstrap-treeview-DTO 帮助类
 * @author Liu Jun at 2018-4-2 14:49:36
 * @since v1.0.0
 */
public class BsTreeviewUtils {

    /**
     *  加载树形数据
     * @param originDatas 原始数据
     * @return 树形数据
     */
    public static <E extends TreeEntity, T extends BsTreeview<E>> List<T> load(Class<T> clazz, List<E> originDatas) {

        if(originDatas == null || originDatas.isEmpty()) {
            return null;
        }

        Map<String, E> nodeCache = new HashMap<>(); //节点标记缓存
        Map<String, List<E>> groupCache = new HashMap<>(); //父节点分组缓存

        String parentId;
        for (E entity : originDatas) {    //遍历一次，按照父父节点ID分组，并且按照ID标记节点
            nodeCache.put(entity.getId(), entity);   //按照ID标记节点
            parentId = entity.getParentId();    //父节点ID
            if(StringUtils.isNotBlank(parentId) && !TreeEntity.ROOT_ID.equals(parentId)) {
				 /*
                 * 按父节点分组缓存节点
                 */
                if(!groupCache.containsKey(parentId)) {   //不存在则初始化
                    groupCache.put(parentId, new ArrayList<>());
                }
                groupCache.get(parentId).add(entity);
            } else {
				  /*
             	  * 如果父节点为空或系统定义的根节点标识，缓存此类节点
             	  */
                if(!groupCache.containsKey(TreeEntity.ROOT_ID)) {   //不存在则初始化
                    groupCache.put(TreeEntity.ROOT_ID, new ArrayList<>());
                }
                groupCache.get(TreeEntity.ROOT_ID).add(entity);
            }
        }

        /*
         * 比较两个缓存，找出根节点
         */
        List<E> roots = new ArrayList<>(); //根节点缓存
        if(groupCache.containsKey(TreeEntity.ROOT_ID)) {    //先处理绝对根数据(父节点为系统设置根字符)
            List<E> rootList = groupCache.get(TreeEntity.ROOT_ID);
            roots.addAll(rootList);
        }
        for(Map.Entry<String, List<E>> entry : groupCache.entrySet()) {    //在处理相对根数据(父节点在集合中不存在)
            if(entry.getKey().equals(TreeEntity.ROOT_ID)) {
                continue;
            }
            E entity = nodeCache.get(entry.getKey());
            if(entity == null) {
                roots.addAll(groupCache.get(entry.getKey()));
            }
        }

        return generateTreeData(clazz, roots, originDatas);
    }

    /**
     * 遍历全部根节点，依次构建各自子树，加入结果集
     * @param roots 根节点
     * @param nodes 全部节点
     * @return 子树集合
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <E extends TreeEntity, T extends BsTreeview<E>> List<T> generateTreeData(Class<T> clazz, List<E> roots, List<E> nodes) {
        List<T> result = new ArrayList<>();
        T treeview;

        for(E root : roots) {  //遍历根节点
            try {
                Constructor[] constructors = clazz.getDeclaredConstructors();   //找出符合条件的构造器
                for(Constructor constructor : constructors) {
                    if(constructor.getParameterCount() == 1) {
                        Class paramType = constructor.getParameterTypes()[0];
                        if(TreeEntity.class.isAssignableFrom(paramType)) {
                            treeview  = (T) constructor.newInstance(root);
                            setChildren(clazz, treeview, nodes); //构建子节点
                            result.add(treeview);
                        }
                    }
                }
            } catch (Exception e) {
                throw new BusinessException("", e);
            }
        }
        return result;
    }

    /**
     *  递归构建树形结构
     * @param fatherNode 父节点
     * @param allDatas  全部数据
     */
    @SuppressWarnings({"unchecked"})
    private static <E extends TreeEntity, T extends BsTreeview<E>> void setChildren(Class<T> clazz, T fatherNode, List<E> allDatas) {
        if(fatherNode == null || allDatas == null || allDatas.isEmpty()) {
            return;
        }

        for(E entity : allDatas) {
            if(entity.getParentId().equals(fatherNode.getId())) {   //如果是father的子节点
                if(fatherNode.getNodes() == null) {
                    fatherNode.setNodes(new ArrayList<>());
                }
                T subNode;
                try {
                    Constructor[] constructors = clazz.getDeclaredConstructors();   //找出符合条件的构造器
                    for(Constructor constructor : constructors) {
                        if(constructor.getParameterCount() == 1) {
                            Class paramType = constructor.getParameterTypes()[0];
                            if(TreeEntity.class.isAssignableFrom(paramType)) {
                                subNode  = (T) constructor.newInstance(entity);
                                fatherNode.getNodes().add(subNode);
                                setChildren(clazz, subNode, allDatas);  //递归处理子节点
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new BusinessException("", e);
                }
            }
        }
    }
}
