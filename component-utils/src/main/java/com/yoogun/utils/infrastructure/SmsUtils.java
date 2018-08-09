package com.yoogun.utils.infrastructure;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 *  短信帮助类-阿里大鱼
 *  @author  Liu Jun at 2018-6-22 11:09:16
 *  @since v1.0.0
 */
public class SmsUtils {

    private static String product;

    private static String domain;

    private static String accessKeyId;

    private static String accessKeySecret;

    private static String templateCode;

    /**
     * 发送短信
     * @param phoneNumbers  手机号,逗号分隔
     * @param dataJson   短信内容
     */
    public static ResCode send(String phoneNumbers, String dataJson) throws ClientException {
        // 初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();

        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，
        // 批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,
        // 验证码类型的短信推荐使用单条调用的方式；
        // 发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(phoneNumbers);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName("大连用友软件有限公司");
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},
        // 您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,
        // 比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(dataJson);

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");

        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        String resCode = sendSmsResponse.getCode();
        if(resCode != null && !resCode.equals("OK")) {
            return ResCode.OK;
        } else {
            return ResCode.valueOf("isv." + resCode);
        }
    }

    /**
     * 发送短信
     * @param phoneNumbers  手机号,逗号分隔
     * @param dataObj   短信内容对象
     */
    public static ResCode send(String phoneNumbers, Object dataObj) throws ClientException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String dataJson = mapper.writeValueAsString(dataObj);
        return send(phoneNumbers, dataJson);
    }

    /**
     * 发送短信
     * @param phoneNumbers  手机号list
     * @param dataObj   短信内容对象
     */
    public static ResCode send(List<String> phoneNumbers, Object dataObj) throws ClientException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String dataJson = mapper.writeValueAsString(dataObj);

        String phoneNumberStr = StringUtils.join(phoneNumbers, ",");
        return send(phoneNumberStr, dataJson);
    }

    /**
     * 发送短信
     * @param phoneNumbers  手机号list
     * @param dataJson   短信内容json字符串
     */
    public static ResCode send(List<String> phoneNumbers, String dataJson) throws ClientException, JsonProcessingException {
        String phoneNumberStr = StringUtils.join(phoneNumbers, ",");
        return send(phoneNumberStr, dataJson);
    }

    /**
     * 发送短信
     * @param phoneNumbers  手机号list
     * @param dataJson   短信内容json字符串
     */
    public static ResCode send(String[] phoneNumbers, String dataJson) throws ClientException, JsonProcessingException {
        String phoneNumberStr = StringUtils.join(phoneNumbers, ",");
        return send(phoneNumberStr, dataJson);
    }

    /**
     * 发送短信
     * @param phoneNumbers  手机号list
     * @param dataObj   短信内容对象
     */
    public static ResCode send(String[] phoneNumbers, Object dataObj) throws ClientException, JsonProcessingException {
        String phoneNumberStr = StringUtils.join(phoneNumbers, ",");
        return send(phoneNumberStr, dataObj);
    }

    @Value("#{utilInitializeProperties['ALIYUN.SMS.PRODUCT']}")
    public void setProduct(String product) {
        SmsUtils.product = product;
    }

    @Value("#{utilInitializeProperties['ALIYUN.SMS.DOMAIN']}")
    public void setDomain(String domain) {
        SmsUtils.domain = domain;
    }

    @Value("#{utilInitializeProperties['ALIYUN.SMS.ACCESS_KEY_ID']}")
    public void setAccessKeyId(String accessKeyId) {
        SmsUtils.accessKeyId = accessKeyId;
    }

    @Value("#{utilInitializeProperties['ALIYUN.SMS.ACCESS_KEY_SECRET']}")
    public void setAccessKeySecret(String accessKeySecret) {
        SmsUtils.accessKeySecret = accessKeySecret;
    }

    @Value("#{utilInitializeProperties['ALIYUN.SMS.TEMPLATE_CODE']}")
    public void setTemplateCode(String templateCode) {
        SmsUtils.templateCode = templateCode;
    }

    /**
     * 阿里云短信返回码枚举
     */
    public enum ResCode {

        OK("成功"),
        RAM_PERMISSION_DENY("RAM权限DENY"),
        OUT_OF_SERVICE("业务停机"),
        PRODUCT_UN_SUBSCRIPT("未开通云通信产品的阿里云客户"),
        PRODUCT_UNSUBSCRIBE("产品未开通"),
        ACCOUNT_NOT_EXISTS("账户不存在"),
        ACCOUNT_ABNORMAL("账户异常"),
        SMS_TEMPLATE_ILLEGAL("短信模板不合法"),
        SMS_SIGNATURE_ILLEGAL("短信签名不合法"),
        INVALID_PARAMETERS("参数异常"),
        SYSTEM_ERROR("系统错误"),
        MOBILE_NUMBER_ILLEGAL("非法手机号"),
        MOBILE_COUNT_OVER_LIMIT("手机号码数量超过限制"),
        TEMPLATE_MISSING_PARAMETERS("模板缺少变量"),
        BUSINESS_LIMIT_CONTROL("业务限流"),
        INVALID_JSON_PARAM("JSON参数不合法，只接受字符串值"),
        BLACK_KEY_CONTROL_LIMIT("黑名单管控"),
        PARAM_LENGTH_LIMIT("参数超出长度限制"),
        PARAM_NOT_SUPPORT_URL("不支持URL"),
        AMOUNT_NOT_ENOUGH("账户余额不足");

        private String message;

        /**
         * 构造器
         * @param message 错误码对应信息
         */
        ResCode(String message) {
            this.message = message;
        }

        @JsonValue
        public String getMessage() {
            return message;
        }
    }
}
