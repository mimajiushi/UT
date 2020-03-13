import org.junit.Test;
import run.ut.app.model.domain.User;

import java.util.HashMap;
import java.util.Map;

public class JWTTest {

    @Test
    public void test(){
        // 单角色
        // 0 0 1 1 & 0 0 1 0 == 0 0 1 0 说明用户拥有此角色
        System.out.println((3 & 2) == 2);

        // 多角色
        // 0 1 1 1 & 0 0 1 1
        System.out.println((7 & 3) == 3);

        System.out.println((7 & 4) == 3);
    }

    @Test
    public void test1(){
        User user = new User();
//                .setUid(21L)
//                .set
//        Map<String, Object> userInfo = new HashMap<>();
//        userInfo.put("uid", user.getUid());
//        userInfo.put("openid", user.getOpenid());
//        userInfo.put("roles", user.getRoles().getName());
    }
}
