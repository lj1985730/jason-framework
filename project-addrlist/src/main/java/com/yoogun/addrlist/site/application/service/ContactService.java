package com.yoogun.addrlist.site.application.service;

import com.yoogun.addrlist.site.domain.model.Contact;
import com.yoogun.addrlist.site.repository.ContactDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ContactService {

    /**
     * 统一注入DAO层
     */
    @Resource
    private ContactDao contactDao;

    /**
     * 获取通讯录名单
     * @param name 姓名
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Contact> getContactList(String name){

        SQL sql = new SQL();
        sql.SELECT("*")
                .FROM("ADDRLIST_CONTACT")
                .WHERE("DELETED = 'F'");

        //模糊查询
        if(StringUtils.isNotBlank(name)) {
            sql.AND();
            sql.WHERE("NAME like '%" + name + "%'");
        }

        return contactDao.search(sql);

    }

    /**
     * 获取联系人信息
     * @param id 联系人主键
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Contact getContact(String id) {

        return contactDao.searchById( Contact.class,id);

    }

}
