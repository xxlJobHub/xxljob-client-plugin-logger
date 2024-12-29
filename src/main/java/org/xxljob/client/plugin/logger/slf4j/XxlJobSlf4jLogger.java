package org.xxljob.client.plugin.logger.slf4j;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.xxljob.client.plugin.logger.XxlJobLogger;
import org.xxljob.client.plugin.logger.XxlJobLoggerFactory;

/**
 * <p>将{@link org.xxljob.client.plugin.logger.XxlJobLogger}桥接到{@link Logger}</p>
 * <p>创建于 2024-12-29 19:39 19:39 </p>
 *
 * @author <a href="mailto:fgwang.660@gmail.com">witt</a>
 * @version v1.0
 * @since 1.0.0
 */
public class XxlJobSlf4jLogger implements Logger {

    /**
     * 完全限定类名，用于获取调用方信息
     */
    private final static String FQCN = XxlJobSlf4jLogger.class.getName();

    private final Logger slf4jLogger;

    private final XxlJobLogger xxlJobLogger;

    public XxlJobSlf4jLogger(Logger slf4jLogger) {
        this.slf4jLogger = slf4jLogger;
        this.xxlJobLogger = XxlJobLoggerFactory.getLogger();
    }

    @Override
    public String getName() {
        return this.slf4jLogger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.slf4jLogger.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        this.slf4jLogger.trace(msg);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void trace(String format, Object arg) {
        this.slf4jLogger.trace(format, arg);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        this.slf4jLogger.trace(format, arg1, arg2);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        this.slf4jLogger.trace(format, arguments);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        this.slf4jLogger.trace(msg, t);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return this.slf4jLogger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {
        this.slf4jLogger.trace(marker, msg);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        this.slf4jLogger.trace(marker, format, arg);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        this.slf4jLogger.trace(marker, format, arg1, arg2);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        this.slf4jLogger.trace(marker, format, argArray);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, format, argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        this.slf4jLogger.trace(marker, msg, t);
        this.xxlJobLogger.trace(FQCN, this.slf4jLogger, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.slf4jLogger.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return this.slf4jLogger.isDebugEnabled(marker);
    }

    @Override
    public void debug(String msg) {
        this.slf4jLogger.debug(msg);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void debug(String format, Object arg) {
        this.slf4jLogger.debug(format, arg);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        this.slf4jLogger.debug(format, arg1, arg2);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        this.slf4jLogger.debug(format, arguments);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        this.slf4jLogger.debug(msg, t);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, msg, t);
    }

    @Override
    public void debug(Marker marker, String msg) {
        this.slf4jLogger.debug(marker, msg);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        this.slf4jLogger.debug(marker, format, arg);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        this.slf4jLogger.debug(marker, format, arg1, arg2);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        this.slf4jLogger.debug(marker, format, arguments);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        this.slf4jLogger.debug(marker, msg, t);
        this.xxlJobLogger.debug(FQCN, this.slf4jLogger, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.slf4jLogger.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return this.slf4jLogger.isInfoEnabled(marker);
    }


    @Override
    public void info(String msg) {
        this.slf4jLogger.info(msg);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void info(String format, Object arg) {
        this.slf4jLogger.info(format, arg);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        this.slf4jLogger.info(format, arg1, arg2);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        this.slf4jLogger.info(format, arguments);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        this.slf4jLogger.info(msg, t);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, msg, t);
    }

    @Override
    public void info(Marker marker, String msg) {
        this.slf4jLogger.info(marker, msg);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        this.slf4jLogger.info(marker, format, arg);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        this.slf4jLogger.info(marker, format, arg1, arg2);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        this.slf4jLogger.info(marker, format, arguments);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        this.slf4jLogger.info(marker, msg, t);
        this.xxlJobLogger.info(FQCN, this.slf4jLogger, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return this.slf4jLogger.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return this.slf4jLogger.isWarnEnabled(marker);
    }

    @Override
    public boolean isErrorEnabled() {
        return this.slf4jLogger.isErrorEnabled();
    }


    @Override
    public void warn(String msg) {
        this.slf4jLogger.warn(msg);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void warn(String format, Object arg) {
        this.slf4jLogger.warn(format, arg);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        this.slf4jLogger.warn(format, arg1, arg2);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        this.slf4jLogger.warn(format, arguments);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, format, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        this.slf4jLogger.warn(msg, t);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, msg, t);
    }

    @Override
    public void warn(Marker marker, String msg) {
        this.slf4jLogger.warn(marker, msg);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        this.slf4jLogger.warn(marker, format, arg);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        this.slf4jLogger.warn(marker, format, arg1, arg2);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        this.slf4jLogger.warn(marker, format, arguments);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        this.slf4jLogger.warn(marker, msg, t);
        this.xxlJobLogger.warn(FQCN, this.slf4jLogger, msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return this.slf4jLogger.isErrorEnabled(marker);
    }

    @Override
    public void error(String msg) {
        this.slf4jLogger.error(msg);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void error(String format, Object arg) {
        this.slf4jLogger.error(format, arg);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        this.slf4jLogger.error(format, arg1, arg2);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        this.slf4jLogger.error(format, arguments);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        this.slf4jLogger.error(msg, t);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, msg, t);
    }

    @Override
    public void error(Marker marker, String msg) {
        this.slf4jLogger.error(marker, msg);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        this.slf4jLogger.error(marker, format, arg);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        this.slf4jLogger.error(marker, format, arg1, arg2);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        this.slf4jLogger.error(marker, format, arguments);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        this.slf4jLogger.error(marker, msg, t);
        this.xxlJobLogger.error(FQCN, this.slf4jLogger, msg, t);
    }
}
