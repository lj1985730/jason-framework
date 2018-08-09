package com.yoogun.utils.application.dto;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.domain.model.TreeEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * bootstrap-treeview-DTO
 * @author Liu Jun at 2018-4-2 14:38:54
 * @since v1.0.0
 */
public abstract class BsTreeview<E extends TreeEntity> implements Serializable {

    private String id;

    protected String text;

    private String icon;

    private String selectedIcon;

    protected String color;

    protected String backColor;

    private String href;

    private Boolean selectable = true;

    private State state;

    private String[] tags;

    private List<BsTreeview> nodes;

    protected Object data;

    /**
     * 构造器
     */
    public BsTreeview(BaseEntity entity) {
        this.id = entity.getId();
        this.state = new State();
        this.nodes = new ArrayList<>();
    }

    /**
     * 构造器
     */
    public BsTreeview(E treeEntity) {
        this.id = treeEntity.getId();
        this.text = treeEntity.getName();
        this.state = new State();
        this.nodes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(String selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getSelectable() {
        return selectable;
    }

    public void setSelectable(Boolean selectable) {
        this.selectable = selectable;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public List<BsTreeview> getNodes() {
        return nodes;
    }

    public void setNodes(List<BsTreeview> nodes) {
        this.nodes = nodes;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     *  节点状态
     */
    public class State {
        private Boolean checked = false;  //勾选
        private Boolean disabled = false;
        private Boolean expanded = false;
        private Boolean selected = false;

        public Boolean getChecked() {
            return checked;
        }

        public void setChecked(Boolean checked) {
            this.checked = checked;
        }

        public Boolean getDisabled() {
            return disabled;
        }

        public void setDisabled(Boolean disabled) {
            this.disabled = disabled;
        }

        public Boolean getExpanded() {
            return expanded;
        }

        public void setExpanded(Boolean expanded) {
            this.expanded = expanded;
        }

        public Boolean getSelected() {
            return selected;
        }

        public void setSelected(Boolean selected) {
            this.selected = selected;
        }
    }
}
