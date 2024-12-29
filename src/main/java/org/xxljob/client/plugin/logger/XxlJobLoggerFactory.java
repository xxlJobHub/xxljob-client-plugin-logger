package org.xxljob.client.plugin.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xxljob.client.plugin.logger.slf4j.XxlJobSlf4jLogger;
import org.xxljob.client.plugin.logger.slf4j.XxlJobSlf4jProxyLogger;

/**
 * <p>{@link XxlJobLogger}对象创建</p>
 * <p>创建于 2024-12-29 17:08 17:08 </p>
 *
 * @author <a href="mailto:fgwang.660@gmail.com">witt</a>
 * @version v1.0
 * @since 1.0.0
 */
public final class XxlJobLoggerFactory {

    /**
     * 全局{@link XxlJobLogger}对象,仅在第一次调用{@link #getLogger()}时初始化
     */
    private static XxlJobLogger DEFAULT_LOGGER;

    /**
     * 通常用于自定义{@link XxlJobLogger}实现
     *
     * @param logger {@link XxlJobLogger}
     * @since 1.0.0
     */
    public static void setLogger(XxlJobLogger logger) {
        if (null == logger) {
            return;
        }

        // 如果相等，表明是默认实现，无须初始化
        if (logger.getClass() == XxlJobLogger.class) {
            return;
        }

        DEFAULT_LOGGER = logger;
    }

    /**
     * 获取{@link XxlJobLogger}对象
     *
     * @return {@link XxlJobLogger }
     * @since 1.0.0
     */
    public static XxlJobLogger getLogger() {
        if (null != DEFAULT_LOGGER) {
            return DEFAULT_LOGGER;
        }

        synchronized (XxlJobLoggerFactory.class) {
            if (null != DEFAULT_LOGGER) {
                return DEFAULT_LOGGER;
            }

            DEFAULT_LOGGER = new XxlJobLogger();
            return DEFAULT_LOGGER;
        }
    }


    /**
     * 获取xxlJob的slf4j日志装饰器
     *
     * @param name 日志类
     * @return {@link Logger }
     * @see Logger
     * @see XxlJobSlf4jLogger
     * @see LoggerFactory
     * @since 1.0.0
     */
    public static Logger getDecorateLogger(String name) {
        return new XxlJobSlf4jLogger(LoggerFactory.getLogger(name));
    }

    /**
     * 获取xxlJob的slf4j日志装饰器
     *
     * @param clazz 日志类
     * @return {@link Logger }
     * @see Logger
     * @see XxlJobSlf4jLogger
     * @see LoggerFactory
     * @since 1.0.0
     */
    public static Logger getDecorateLogger(Class<?> clazz) {
        return new XxlJobSlf4jLogger(LoggerFactory.getLogger(clazz));
    }

    /**
     * 获取xxlJob的slf4j代理日志对象
     *
     * @param name 日志类
     * @return {@link Logger }
     */
    public static Logger getProxyLogger(String name) {
        return XxlJobSlf4jProxyLogger.getLogger(LoggerFactory.getLogger(name));
    }

    /**
     * 获取xxlJob的slf4j代理日志对象
     *
     * @param clazz 日志类
     * @return {@link Logger }
     */
    public static Logger getProxyLogger(Class<?> clazz) {
        return XxlJobSlf4jProxyLogger.getLogger(LoggerFactory.getLogger(clazz));
    }
}
