package run.ut.app.netty.task;

import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author chenwenjie.star
 * @date 2021/4/27 4:33 下午
 */
@Slf4j
public class RetryTimerTask implements TimerTask {

    // 每隔几秒执行一次
    private final long tick;

    // 最大重试次数
    private final int retries;

    private int retryTimes = 0;

    private TimerTask task;

    public RetryTimerTask(TimerTask task, long tick, int retries) {
        this.tick = tick;
        this.retries = retries;
        this.task = task;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        if (timeout.isCancelled() || timeout.isExpired()) {
            return;
        }
        task.run(timeout);
        if ((++retryTimes) >= retries) {
            // 重试次数超过了设置的值
            log.debug("失败重试次数超过阈值: {}，不再重试", retries);
            timeout.cancel();
        } else {
            rePut(timeout);
        }
    }

    // 通过 timeout 拿到 timer 实例，重新提交一个定时任务
    private void rePut(Timeout timeout) {
        if (timeout == null) {
            return;
        }
        Timer timer = timeout.timer();
        if (timeout.isCancelled()) {
            return;
        }
        timer.newTimeout(timeout.task(), tick, TimeUnit.SECONDS);
    }
}