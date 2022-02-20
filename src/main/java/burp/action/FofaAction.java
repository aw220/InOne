package burp.action;

import com.r4v3zn.fofa.core.DO.FofaData;
import fofa.FofaClient;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static burp.Utils.*;
import static cn.hutool.core.text.CharSequenceUtil.isBlank;

public class FofaAction {
    // 线程池
    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4,
            new BasicThreadFactory.Builder().namingPattern("task-schedule-pool-%d").daemon(true).build());

    public void search(int page, String query) {
        stdout.println(page);
        FofaClient client = new FofaClient(FOFA_ID, FOFA_KEY);
        if (!isBlank(query)) {
            executorService.schedule(() -> {
                String fields = String.join(",", HEADER);
                FofaData fofaData = null;
                try {
                    fofaData = client.getData(query, page, pageSize, fields);
                } catch (Exception exception) {
                    stderr.println("error: " + exception);
                    exception.printStackTrace();
                }
                assert fofaData != null;
                // 将返回数据丢进result保存，后面新增的自然是要存在List的后面
                result.addAll(fofaData.getResults());
                stdout.println("result.size()：" + result.size());
                stdout.println("Current Thread" + Thread.currentThread().getName());

                latch.countDown();
            }, 0, TimeUnit.SECONDS);
        }
    }

    // 自动加载数据，因为FOFA网速太慢了。。。
    public void autoSearch(int page, String query) {
        if (result.size() - 40 < page * tableSize) {
            stdout.println("autoSearch：start");
            search(page, query);
            stdout.println("autoSearch：over");
        }
    }

}
