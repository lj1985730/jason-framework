package com.yoogun.auth.application.dto;

import com.yoogun.auth.domain.model.Button;
import com.yoogun.auth.domain.model.Menu;
import com.yoogun.utils.application.dto.BsTreeview;

import java.util.ArrayList;
import java.util.List;

/**
 * bootstrap-treeview-menuAndBtn DTO
 * @author Liu Jun at 2018-3-2 10:21:01
 * @since v1.0.0
 */
public class MenuBtnTreeview extends BsTreeview<Menu> {

    /**
     * 构造器
     */
    public MenuBtnTreeview(Menu menu) {
        super(menu);
        this.setSelectable(false);

        /* 组装menu下的button */
        List<Button> buttons =  menu.getButtons();
        // 按钮HTML文本模板
        String buttonFmt =
                "<label>" +
                    "<input type='checkbox' class='menu-btn' id='%s' /> %s" +
                "</label>";
        if(buttons != null && !buttons.isEmpty()) {
            // 固定开头HTML内容
            StringBuilder builder = new StringBuilder("<div class='input-group pull-right'><div class='icheck-inline'>可用按钮：");
            List<String> tagsList = new ArrayList<>();
            for(Button button : buttons) {  //拼接按钮HTML内容
                builder.append(String.format(buttonFmt, button.getId(), button.getName()));
            }
            builder.append("</div></div>"); //HTML结尾
            this.text += builder.toString();
        }
    }
}
