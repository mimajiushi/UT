package run.ut.app.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.config.thirdapi.SmsConfig;
import run.ut.app.config.thirdapi.ThirdApiConfig;
import run.ut.app.exception.SmsCodeErrorException;
import run.ut.app.exception.SmsSendException;
import run.ut.app.service.RedisService;
import run.ut.app.service.SmsService;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsServiceImpl implements SmsService {

    private final RedisService redisService;
    private final ThirdApiConfig thirdApiConfig;
    private final SmsSingleSender smsSingleSender;

    @Override
    public void sendCode(String tel, String code) {
        SmsConfig smsConfig = thirdApiConfig.getSms();

        // Add template parameters
        ArrayList<String> params = new ArrayList<>();
        params.add(code);
        params.add(smsConfig.getValidMin());

        try {
            SmsSingleSenderResult result = smsSingleSender
                    .sendWithParam("86", tel, smsConfig.getTemplateId(), params, smsConfig.getSign(), "", "");
            if (result.result == 0){
                String key = RedisConfig.SMS_LOGIN_PREFIX+":"+tel;
                redisService.setKeyValTTL(key, code, RedisConfig.SMS_TIME_OUT);
            }else{
                // todo 还有其它返回码需要处理
                throw new SmsSendException("短信发送失败！");
            }
        } catch (Exception e) {
            log.error("验证码发送异常，手机号：{}，错误信息：{}", tel, e.getMessage());
            throw new SmsSendException("短信发送失败！", e);
        }

    }

    @Override
    public void checkCode(String tel, String code) {
        String key = RedisConfig.SMS_LOGIN_PREFIX+":"+tel;
        String res = redisService.get(key);
        if (StringUtils.isEmpty(res)){
            throw new SmsCodeErrorException("验证码已过期！");
        }
        if (!res.equals(code)){
            throw new SmsCodeErrorException("验证码错误！");
        }
    }
}
