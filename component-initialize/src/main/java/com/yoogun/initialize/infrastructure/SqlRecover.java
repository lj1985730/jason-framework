package com.yoogun.initialize.infrastructure;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 脚本还原器
 * <p>还原：将脚本中的占位符转换为真实数据。
 * 其中，主键相关转为新UUID
 */
public class SqlRecover {

    /**
     * 重写缓存
     * <p> 用于储存重写时生成的临时数据
     */
    private Map<String, String> rewrittenMap = new HashMap<>();

    /**
     * 企业Id
     */
    private String tenantId;

    /**
     * 超管Id
     */
    private String superAdminId;

    /**
     * 用友企业Id（超管企业id）
     */
    private String yonyouId;

    /**
     * 构造方法
     * @param tenantId 租户ID
     * @param superAdminId    超管ID
     * @param yonyouId    用友企业Id（超管企业id）
     */
    public SqlRecover(String tenantId, String superAdminId, String yonyouId) {
        this.tenantId = tenantId;
        this.superAdminId = superAdminId;
        this.yonyouId = yonyouId;
    }

    /**
     * 重写SQL字符串
     * <p> 重写：将占位符改写成真实要写入数据库的内容
     * @param sql SQL字符串
     * @param repeat 重写外键次数,默认1次。自关联表可能需要重写2次以上
     * @return 重写后的SQL字符串
     */
    public String recover(String sql, Integer... repeat) {

        if(StringUtils.isBlank(sql)) {
            return "";
        }

        String result = this.rewriteTenant(sql);	//重写企业ID
        result = this.rewriteAdmin(result);		//重写超管ID
        result = this.rewriteYonyouId(result);		//重写用友ID
        result = this.rewriteConstraintId(result);	//重写受约束主键

        if(repeat != null && repeat.length > 0) {   //重复重写，适用于自关联等情况
            for (int i = 1; i < repeat[0]; i ++) {
                result = this.rewriteConstraintId(result);	//重写受约束主键
            }
        }

        if(result.contains( "${UUID" )) {
            throw new InitializeException("重写脚本出现遗漏：" + result);
        }

        return result;
    }

    /**
     * 重写企业Id
     * @param sql 待重写脚本
     * @return 重写后脚本
     */
    private String rewriteTenant(String sql) {
        return StringUtils.replace(sql, SqlPlaceholder.TENANT_ID.getPlaceholder(), this.tenantId);	//重写企业ID
    }

    /**
     * 重写超管Id
     * @param sql 待重写脚本
     * @return 重写后脚本
     */
    private String rewriteAdmin(String sql) {
        return StringUtils.replace(sql, SqlPlaceholder.SUPER_ADMIN_ID.getPlaceholder(), this.superAdminId);	//重写超管ID
    }

    /**
     * 重写用友Id
     * @param sql 待重写脚本
     * @return 重写后脚本
     */
    private String rewriteYonyouId(String sql) {
        return StringUtils.replace(sql, SqlPlaceholder.YONYOU_ID.getPlaceholder(), this.yonyouId);	//重写用友Id
    }

    /**
     * 重写存在约束的ID
     * @param sql 待重写脚本
     * @return 重写后脚本
     */
    private String rewriteConstraintId(String sql) {

        String regex = "\\$\\{UUID-\\w+-\\d+\\}";
        List<String> matchGroups = this.findMatchGroup(sql, regex);

        for(String group : matchGroups) {
            if(!rewrittenMap.containsKey(group)) {
                String uuid = UUID.randomUUID().toString().toUpperCase();    //生成新的UUID
                rewrittenMap.put( group, uuid );    //将ID写入缓存
            }

            if(SqlProcessor.lobIndexCache.containsKey( "'" + group + "'" )) {   //将缓存中的占位符替换成真实UUID
                List<Integer> indexList = SqlProcessor.lobIndexCache.remove( "'" + group + "'" );   //取出原entry
                SqlProcessor.lobIndexCache.put( "'" + rewrittenMap.get(group) + "'" , indexList );   //替换key后存入新entry
            }

            sql = StringUtils.replace(sql, group, rewrittenMap.get(group));
        }

        return sql;	//替换内容
    }

    /**
     * 获取原字符串中符合正则规则的字符串集合
     * @param src 原字符串
     * @param regex 正则表达式
     * @return 符合规则的字符串集合
     */
    private List<String> findMatchGroup(String src, String regex) {

        if(StringUtils.isBlank(src) || StringUtils.isBlank(regex)) {
            throw new NullPointerException("待验证的字符串或正则表达式为空！");
        }

        List<String> groups = new ArrayList<>();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(src);

        while(matcher.find()){
            groups.add(matcher.group());
        }

        return groups;
    }
}
