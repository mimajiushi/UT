import cn.hutool.crypto.digest.BCrypt;
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
public class BCryptTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1(){
        String password = BCrypt.hashpw("mimajiushi", BCrypt.gensalt());
        System.out.println(password);
        System.out.println(BCrypt.checkpw("mimajiushi", password));
        System.out.println(BCrypt.checkpw("mimajiushi2", password));
    }

//    @Test
//    public void insert() throws InterruptedException {
//        User user = User.builder().build();
//        user.setAvatar("1");
//        user.setEmail("1");
//        user.setPassword("1");
//        user.setUsername("1");
//        user.setPhoneNumber(1);
//        this.userMapper.insert(user);
////        userMapper.deleteById(2);
//    }
}
