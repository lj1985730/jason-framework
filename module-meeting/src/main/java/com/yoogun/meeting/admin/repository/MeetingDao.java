package com.yoogun.meeting.admin.repository;

import com.yoogun.meeting.admin.domain.model.Meeting;
import com.yoogun.core.repository.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingDao extends BaseDao<Meeting>{
}
