package com.yoogun.utils.application.dto;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.domain.model.TreeEntity;

/**
 * bootstrap-treeview-common DTO
 * @author Liu Jun at 2018-4-2 16:07:38
 * @since v1.0.0
 */
public class CommonTreeview extends BsTreeview<TreeEntity> {

    public CommonTreeview(BaseEntity entity) {
        super(entity);
    }

    public CommonTreeview(TreeEntity treeEntity) {
        super(treeEntity);
    }
}
