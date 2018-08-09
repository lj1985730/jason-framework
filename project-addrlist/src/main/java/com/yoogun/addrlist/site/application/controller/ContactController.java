package com.yoogun.addrlist.site.application.controller;

import com.yoogun.addrlist.site.application.service.ContactService;
import com.yoogun.addrlist.site.domain.model.Contact;
import com.yoogun.core.application.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 通讯录-联系人-控制层
 * @author Sheng Baoyu at 2016-8-16 22:13:53
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/site/addrlist/")
 class ContactController extends BaseController{

    @Resource
    private ContactService contactService;

    /**
     * 联系人列表页面
     */
    @RequestMapping(value = "/contactList", method = RequestMethod.GET)
    public String addrlistHome(Model model,String name) {
        //查询通讯录
        List<Contact> contact = contactService.getContactList(name);

        model.addAttribute("contacts",contact);
        return pageView("/addrlist/site/contactList");
    }

    /**
     * 联系人详细信息页
     * @return 角色菜单
     */
    @RequestMapping(value = "/contactDetatil", method = RequestMethod.GET)
    public String contactHome(String id, Model model) {
        Contact person = new Contact();
        if (id != null){
            person = contactService.getContact(id);
        }
        model.addAttribute("person",person);
        return pageView("/addrlist/site/contactInfo");
    }
}
