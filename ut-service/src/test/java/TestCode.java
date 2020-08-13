import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.Test;

import java.util.List;
import java.util.Map;

@Data
class DemoObj {
    private String log_id;
    private Integer words_result_num;
    private List<Map<String, String>> words_result;
}

public class TestCode {

    @Test
    public void test(){
        String json = "{'log_id': 4964852419019311123, 'words_result_num': 1, 'words_result': [{'words': ' G6zb'}]}";
        DemoObj demoObj = JSON.parseObject(json, DemoObj.class);
        System.out.println(demoObj.getWords_result().get(0).get("words"));
    }
}
