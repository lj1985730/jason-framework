package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 权限-人员岗位关系-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Table(name = "AUTH_PERSON_POST")
public class PersonPost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Person person;

    /**
     * 人员ID
     **/
    @NotBlank(message = "‘人员ID’不能为空")
    @Length(max = 36, message = "‘人员ID’内容长度不能超过36")
    @Column(name = "PERSON_ID")
    private String personId;

    private Post post;

    /**
     * 岗位ID
     **/
    @NotBlank(message = "‘岗位ID’不能为空")
    @Length(max = 36, message = "‘岗位ID’内容长度不能超过36")
    @Column(name = "POST_ID")
    private String postId;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post sysPost) {
        this.post = post;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}