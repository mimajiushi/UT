import cn.hutool.crypto.digest.BCrypt;
import org.junit.Test;

public class BCryptTest {
    @Test
    public void test1(){
        String password = BCrypt.hashpw("mimajiushi", BCrypt.gensalt());
        System.out.println(password);
        System.out.println(BCrypt.checkpw("mimajiushi", password));
        System.out.println(BCrypt.checkpw("mimajiushi2", password));
    }
}
