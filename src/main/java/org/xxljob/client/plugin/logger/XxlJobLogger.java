package org.xxljob.client.plugin.logger;

import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.log.XxlJobFileAppender;
import com.xxl.job.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>{@code xxl-job}的日志输出类</p>
 * <p>
 * 日志级别遵循{@link Level}, 默认格式: {@code 时间 level PID --- [TRACE] {Context} [THREAD] class#method:line:message : exception},
 * 其中：
 *     <ul>
 *         <li>日期格式可通过{@link #setDateFormat(String)}自定义</li>
 *         <li>pid可通过{@link #isPid(boolean)}函数禁用</li>
 *         <li>tracing的key可通过{@link #setTracingKey(String)}自定义</li>
 *         <li>所有格式均可通过重写{@code mark*}函数自定义</li>
 *     </ul>
 * </p>
 * <p>创建于 2024-12-29 00:44 00:44 </p>
 *
 * @author <a href="mailto:fgwang.660@gmail.com">witt</a>
 * @version v1.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class XxlJobLogger {

    /**
     * 完全限定类名，用于获取调用方信息
     */
    private final static String FQCN = XxlJobLogger.class.getName();

    /**
     * 当前进程pid
     */
    protected long pid = -1;

    /**
     * 是否输出pid
     */
    protected boolean isPid = true;

    /**
     * 设置日志输出的日期时间格式
     */
    protected String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 基于OpenTracing的traceId
     */
    protected String tracingKey = "traceId";

    /**
     * 需要标记追加到日志中的上下文Key
     */
    protected final Set<String> contextKeys = new CopyOnWriteArraySet<>();

    protected XxlJobLogger() {
        this.pid = this.getPid();
    }

    /**
     * 是否输出pid,默认true
     *
     * @param show 是否输出pid
     */
    public void isPid(boolean show) {
        this.isPid = show;
    }

    /**
     * 设置日志输出的日期时间格式
     *
     * @param dateFormat 日期时间格式
     */
    public void setDateFormat(String dateFormat) {
        if (null != dateFormat) {
            this.dateFormat = dateFormat;
        }
    }

    /**
     * 设置基于OpenTracing的traceId
     *
     * @param key traceId key
     */
    public void setTracingKey(String key) {
        this.tracingKey = key;
    }

    /**
     * 设置需要标记追加到日志中的上下文Key
     *
     * @param keys 上下文Key
     */
    public void setContextKeys(Set<String> keys) {
        if (null == keys) {
            this.contextKeys.clear();
            return;
        }

        this.contextKeys.addAll(keys);
        this.contextKeys.remove("");

        // 都存在于上下文中，防止重复输出
        if (null != this.tracingKey) {
            this.contextKeys.remove(this.tracingKey);
        }
    }

    /**
     * 输出日志
     *
     * @param fqcn        调用者类
     * @param slf4jLogger slf4jLogger
     * @param level       日志级别
     * @param message     日志信息
     * @param ex          异常
     * @param args        参数
     */
    public void log(Class<?> fqcn, Logger slf4jLogger, Level level, String message, Throwable ex, Object[] args) {
        this.handlerNormalizedMessage(fqcn.getName(), slf4jLogger, level, message, args, ex);
    }

    /**
     * 输出日志
     *
     * @param fqcn    调用者类
     * @param level   日志级别
     * @param message 日志信息
     * @param ex      异常
     * @param args    参数
     */
    public void log(Class<?> fqcn, Level level, String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(fqcn.getName(), level, message, args, ex);
    }

    /**
     * 输出日志
     *
     * @param fqcn    调用者类
     * @param level   日志级别
     * @param message 日志信息
     * @param args    参数
     */
    public void log(Class<?> fqcn, Level level, String message, Object... args) {
        this.handlerNormalizedMessage(fqcn.getName(), level, message, args, null);
    }

    /**
     * 输出{@code trace}日志
     *
     * @param message 日志信息
     * @param args    参数
     */
    public void trace(String message, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.TRACE, message, args, null);
    }

    /**
     * 输出{@code trace}日志
     *
     * @param fqcn        完全限定类名
     * @param message     日志信息
     * @param slf4jLogger slf4jLogger
     * @param args        参数
     */
    public void trace(String fqcn, Logger slf4jLogger, String message, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.TRACE, message, args, null);
    }

    /**
     * 输出{@code trace}日志
     *
     * @param message 日志信息
     * @param ex      异常
     * @param args    参数
     */
    public void trace(String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.TRACE, message, args, ex);
    }

    /**
     * 输出{@code trace}日志
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param ex          异常
     * @param args        参数
     */
    public void trace(String fqcn, Logger slf4jLogger, String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.TRACE, message, args, ex);
    }

    /**
     * 输出{@code debug}日志
     *
     * @param message 日志信息
     * @param args    参数
     */
    public void debug(String message, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.DEBUG, message, args, null);
    }

    /**
     * 输出{@code debug}日志
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param args        参数
     */
    public void debug(String fqcn, Logger slf4jLogger, String message, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.DEBUG, message, args, null);
    }

    /**
     * 输出{@code debug}日志
     *
     * @param message 日志信息
     * @param ex      异常
     * @param args    参数
     */
    public void debug(String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.DEBUG, message, args, ex);
    }

    /**
     * 输出{@code debug}日志
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param ex          异常
     * @param args        参数
     */
    public void debug(String fqcn, Logger slf4jLogger, String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.DEBUG, message, args, ex);
    }

    /**
     * 输出{@code info}日志
     *
     * @param message 日志信息
     * @param args    参数
     */
    public void info(String message, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.INFO, message, args, null);
    }

    /**
     * 输出{@code info}日志
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param args        参数
     */
    public void info(String fqcn, Logger slf4jLogger, String message, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.INFO, message, args, null);
    }


    /**
     * 输出{@code info}日志
     *
     * @param message 日志信息
     * @param ex      异常
     * @param args    参数
     */
    public void info(String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.INFO, message, args, ex);
    }

    /**
     * 输出{@code info}日志
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param ex          异常
     * @param args        参数
     */
    public void info(String fqcn, Logger slf4jLogger, String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.INFO, message, args, ex);
    }

    /**
     * 输出{@code warn}日志
     *
     * @param message 日志信息
     * @param args    参数
     */
    public void warn(String message, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.WARN, message, args, null);
    }

    /**
     * 输出{@code warn}日志
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param args        参数
     */
    public void warn(String fqcn, Logger slf4jLogger, String message, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.WARN, message, args, null);
    }


    /**
     * 输出{@code warn}日志
     *
     * @param message 日志信息
     * @param ex      异常
     * @param args    参数
     */
    public void warn(String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.WARN, message, args, ex);
    }

    /**
     * 输出{@code warn}日志
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param ex          异常
     * @param args        参数
     */
    public void warn(String fqcn, Logger slf4jLogger, String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.WARN, message, args, ex);
    }

    /**
     * 输出{@code error}日志
     *
     * @param message 日志信息
     * @param args    参数
     */
    public void error(String message, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.ERROR, message, args, null);
    }

    /**
     * 输出{@code error}日志
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param args        参数
     */
    public void error(String fqcn, Logger slf4jLogger, String message, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.ERROR, message, args, null);
    }


    /**
     * 输出{@code error}日志
     *
     * @param message 日志信息
     * @param ex      异常
     * @param args    参数
     */
    public void error(String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(FQCN, Level.ERROR, message, args, ex);
    }

    /**
     * 输出{@code error}日志
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param ex          异常
     * @param args        参数
     */
    public void error(String fqcn, Logger slf4jLogger, String message, Throwable ex, Object... args) {
        this.handlerNormalizedMessage(fqcn, slf4jLogger, Level.ERROR, message, args, ex);
    }

    /**
     * 追加到xxlJob日志文件，当前不是xxlJob任务调用，直接忽略
     *
     * @param fqcn        完全限定类名
     * @param slf4jLogger slf4jLogger
     * @param message     日志信息
     * @param args        参数
     * @param e           异常
     */
    private void handlerNormalizedMessage(String fqcn, Logger slf4jLogger, Level level, String message, Object[] args, Throwable e) {
        final XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        if (xxlJobContext == null) {
            return;
        }

        final String logFileName = xxlJobContext.getJobLogFileName();
        if (null == logFileName || logFileName.isEmpty()) {
            return;
        }

        final StackTraceElement caller = this.getCaller(fqcn);
        // 优先使用 slf4jLogger
        Logger logger = slf4jLogger;
        if (null == logger) {
            logger = this.getLogger(caller);
        }

        // 日志级别忽略，忽略规则同调用方Logger
        if (!this.isLevelEnabled(level, logger)) {
            return;
        }

        final StringBuilder log = new StringBuilder();
        this.markDate(new Date(), log);
        this.markLevel(level, log);
        this.markPid(log);

        this.markTrace(log);
        this.markContext(log);

        this.markThread(Thread.currentThread(), log);
        this.markCaller(caller, log);
        this.markMessage(message, args, e, log);

        XxlJobFileAppender.appendLog(logFileName, log.toString());
    }

    /**
     * 追加到xxlJob日志文件，当前不是xxlJob任务调用，直接忽略
     *
     * @param fqcn    完全限定类名
     * @param message 日志信息
     * @param args    参数
     * @param e       异常
     */
    private void handlerNormalizedMessage(String fqcn, Level level, String message, Object[] args, Throwable e) {
        this.handlerNormalizedMessage(fqcn, null, level, message, args, e);
    }


    /**
     * 日志级别是否开启
     *
     * @param level  当前日志级别
     * @param logger 调用方{@link Logger}对象
     * @return boolean
     */
    protected boolean isLevelEnabled(Level level, Logger logger) {
        if (null == logger) {
            return true;
        }

        return logger.isEnabledForLevel(level);
    }

    /**
     * 构造日志日期信息
     *
     * @param date           日期时间
     * @param messageBuilder 日志文本构造器
     */
    protected void markDate(Date date, StringBuilder messageBuilder) {
        messageBuilder.append(DateUtil.format(date, this.dateFormat)).append(' ');
    }

    /**
     * 构造日志级别信息
     *
     * @param level          级别
     * @param messageBuilder 日志文本构造器
     */
    protected void markLevel(Level level, StringBuilder messageBuilder) {
        messageBuilder.append(String.format("%5s", level.toString())).append(' ');
    }

    /**
     * 构造日志进程信息
     *
     * @param messageBuilder 日志文本构造器
     */
    protected void markPid(StringBuilder messageBuilder) {
        // close
        if (!this.isPid) {
            return;
        }

        final long pid = this.pid;
        if (-1 == pid) {
            return;
        }

        messageBuilder.append(pid).append(" --- ");
    }

    /**
     * 向日志上追加当前线程的Trace信息,格式: {@code [traceId]}，如果traceId不存在，则追加{@code [N/A]}
     *
     * @param messageBuilder 日志文本构造器
     */
    public void markTrace(StringBuilder messageBuilder) {
        if (null == this.tracingKey) {
            return;
        }

        String traceValue = MDC.get(this.tracingKey);
        if (null == traceValue || traceValue.isEmpty()) {
            traceValue = "N/A";
        }

        messageBuilder.append('[').append(traceValue).append(']').append(' ');
    }

    /**
     * 向日志上追加上下文信息,格式: {@code {key1=value1,key2=value2}}
     *
     * @param messageBuilder 日志文本构造器
     */
    public void markContext(StringBuilder messageBuilder) {
        if (this.contextKeys.isEmpty()) {
            return;
        }

        boolean isAppend = false;
        for (String key : this.contextKeys) {
            final String value = MDC.get(key);
            if (null == value || value.isEmpty()) {
                continue;
            }

            if (isAppend) {
                messageBuilder.append(",");
            } else {
                messageBuilder.append('{');
            }

            isAppend = true;
            messageBuilder.append(key).append("=").append(value);
        }

        if (isAppend) {
            messageBuilder.append('}').append(' ');
        }
    }

    /**
     * 构造日志线程信息
     *
     * @param thread         县城
     * @param messageBuilder 日志文本构造器
     */
    protected void markThread(Thread thread, StringBuilder messageBuilder) {
        messageBuilder.append('[').append(String.format("%5s", thread.getName())).append(']').append(' ');
    }

    /**
     * 构造日志调用方信息
     *
     * @param caller         调用方
     * @param messageBuilder 日志文本构造器
     */
    protected void markCaller(StackTraceElement caller, StringBuilder messageBuilder) {
        if (null == caller) {
            messageBuilder.append("Unknown").append(" : ");
            return;
        }
        messageBuilder.append(caller.getClassName()).append("#").append(caller.getMethodName()).append(':').append(caller.getLineNumber()).append(" : ");
    }

    /**
     * 构造日志信息
     *
     * @param message        打印输出信息
     * @param messageBuilder 日志文本构造器
     */
    protected void markMessage(String message, Object[] args, Throwable e, StringBuilder messageBuilder) {
        boolean hasMessage = false;
        if (null != message) {
            hasMessage = true;
            // 格式化{}占位符
            messageBuilder.append(MessageFormatter.arrayFormat(message, args, e).getMessage());
        }


        if (null == e) {
            return;
        }

        // message 和 异常堆栈分隔
        if (hasMessage) {
            messageBuilder.append(" :");
        }

        // 使用StringBuilderWriter构造，尽可能减少内存占用
        try (PrintWriter pw = new PrintWriter(new StringBuilderWriter(messageBuilder))) {
            e.printStackTrace(pw);
        }

    }


    /**
     * 获取当前进程 PID
     *
     * @return long pid, -1 为非法值
     */
    protected long getPid() {
        try {
            final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            final String name = runtime.getName();
            final String pid = name.substring(0, name.indexOf('@'));
            return Long.parseLong(pid);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 获取调用方信息
     *
     * @param fqcn 完全限定类名
     * @return {@link StackTraceElement }
     */
    protected StackTraceElement getCaller(String fqcn) {
        try {
            final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (final StackTraceElement element : stackTrace) {
                if (!element.getClassName().equals(fqcn) && !element.getClassName().startsWith(Thread.class.getName())) {
                    return element;
                }
            }
        } catch (Throwable ex) {
            return null;
        }
        return null;
    }

    /**
     * 获取当前类的{@link Logger}对象
     *
     * @param caller 调用方
     * @return {@link Logger }
     */
    protected Logger getLogger(StackTraceElement caller) {
        if (null == caller) {
            return null;
        }
        return LoggerFactory.getLogger(caller.getClassName());
    }


    /**
     * 基于{@link StringBuilder}实现的{@link Writer}
     *
     * @author witt
     */
    private static class StringBuilderWriter extends Writer implements Serializable {

        private static final long serialVersionUID = -971136813713692050L;

        private final StringBuilder builder;

        public StringBuilderWriter() {
            this.builder = new StringBuilder();
        }

        public StringBuilderWriter(int capacity) {
            this.builder = new StringBuilder(capacity);
        }

        public StringBuilderWriter(StringBuilder builder) {
            this.builder = builder != null ? builder : new StringBuilder();
        }

        public Writer append(char value) {
            this.builder.append(value);
            return this;
        }

        public Writer append(CharSequence value) {
            this.builder.append(value);
            return this;
        }

        public Writer append(CharSequence value, int start, int end) {
            this.builder.append(value, start, end);
            return this;
        }

        public void close() {
        }

        public void flush() {
        }

        public void write(String value) {
            if (value != null) {
                this.builder.append(value);
            }

        }

        public void write(char[] value, int offset, int length) {
            if (value != null) {
                this.builder.append(value, offset, length);
            }

        }

        public StringBuilder getBuilder() {
            return this.builder;
        }

        public String toString() {
            return this.builder.toString();
        }
    }

}
