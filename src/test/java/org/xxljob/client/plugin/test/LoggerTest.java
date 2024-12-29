package org.xxljob.client.plugin.test;

import org.xxljob.client.plugin.logger.XxlJobLogger;
import org.xxljob.client.plugin.logger.XxlJobLoggerFactory;

import java.util.Arrays;

/**
 * <p>测试{@link XxlJobLogger}</p>
 * <p>创建于 2024-12-29 17:07 17:07 </p>
 *
 * @author <a href="mailto:fgwang.660@gmail.com">witt</a>
 * @version v1.0
 * @since 1.0.0
 */
public class LoggerTest {

    public void test() {
        XxlJobLogger logger = XxlJobLoggerFactory.getLogger();
        logger.info("test info log.");
        logger.info("test info param1 {} log.", 1);
        logger.info("test info param2 {},{} log.", 1, Arrays.asList("1", 19));
    }


    /**
     * 测试{@link XxlJobLoggerFactory#setLogger(XxlJobLogger)}
     */
    public void test2() {
        XxlJobLogger logger1 = XxlJobLoggerFactory.getLogger();
        XxlJobLogger a = new XxlJobLogger(){
            @Override
            protected void markPid(StringBuilder messageBuilder) {
                messageBuilder.append(this.pid).append(' ');
            }
        };

        XxlJobLoggerFactory.setLogger(a);

        XxlJobLogger logger = XxlJobLoggerFactory.getLogger();

        System.out.println(logger1 == logger);
    }
}
