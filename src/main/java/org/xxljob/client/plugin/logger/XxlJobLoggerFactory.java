package org.xxljob.client.plugin.logger;

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
}
