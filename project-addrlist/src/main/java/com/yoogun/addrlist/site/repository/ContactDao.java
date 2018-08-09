package com.yoogun.addrlist.site.repository;

import com.yoogun.addrlist.site.domain.model.Contact;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Component;


@Component
public interface ContactDao extends BaseDao<Contact> {
}
