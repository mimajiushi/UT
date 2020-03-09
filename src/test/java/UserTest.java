import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.ut.app.UtApplication;
import run.ut.app.mapper.UserMapper;
import run.ut.app.model.domain.User;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtApplication.class)
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1(){
        User phone_numner = userMapper.selectOne(new QueryWrapper<User>().eq("phone_number", "15521245562"));
        System.out.println(phone_numner);
    }
}
