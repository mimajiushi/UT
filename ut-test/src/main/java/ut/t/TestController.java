package ut.t;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.service.ActivityAppointmentService;

@RestController
public class TestController {

    @DubboReference
    private ActivityAppointmentService activityAppointmentService;

    @GetMapping("/test1")
    public String test1(String name) {
        System.out.println(activityAppointmentService);
        return "success";
    }

}
