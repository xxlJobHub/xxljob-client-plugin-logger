package org.xxljob.client.plugin.logger.slf4j;

import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.xxljob.client.plugin.logger.XxlJobLogger;
import org.xxljob.client.plugin.logger.XxlJobLoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>{@link Logger}的动态代理实现，该类通过{@link Proxy}的方式，将日志的目的地分为两处，一处去往原始的日志输出，
 * 另一处去往{@link XxlJobLogger}</p>
 * <p>创建于 2024-12-29 20:18 20:18 </p>
 *
 * @author <a href="mailto:fgwang.660@gmail.com">witt</a>
 * @version v1.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class XxlJobSlf4jProxyLogger implements InvocationHandler {

    private final Logger slf4jLogger;

    private XxlJobSlf4jProxyLogger(Logger logger) {
        this.slf4jLogger = logger;
    }

    /**
     * 通过动态代理创建{@link Logger}对象
     *
     * @param logger 实际{@link Logger}
     * @return {@link Logger }
     */
    public static Logger getLogger(Logger logger) {
        return (Logger) Proxy.newProxyInstance(
                Logger.class.getClassLoader(),
                new Class[]{Logger.class},
                new XxlJobSlf4jProxyLogger(logger)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String methodName = method.getName();
        final String upperCaseMethodName = methodName.toUpperCase();
        final Optional<Level> sameMethodNameLevel = Arrays.stream(Level.values())
                .filter(level -> level.toString().equals(upperCaseMethodName))
                .findFirst();

        // 非level相关的方法调用
        if (!sameMethodNameLevel.isPresent()) {
            return method.invoke(this.slf4jLogger, args);
        }

        final Level level = sameMethodNameLevel.get();
        final XxlJobLogger logger = XxlJobLoggerFactory.getLogger();

        // 获取当前函数所在的类
        final Class<?> fqcn = method.getDeclaringClass();
        final Logger slf4jLogger = (Logger) proxy;

        Throwable ex = null;
        String formatMessage = null;
        int formatMessageParameterIndex = -1, argumentParameterIndex = -1, exceptionParameterIndex = -1;
        final Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0, parameterTypesLength = parameterTypes.length; i < parameterTypesLength; i++) {
            final Class<?> parameterType = parameterTypes[i];

            // 第一个参数是message
            if (formatMessageParameterIndex == -1 && parameterType == String.class) {
                formatMessageParameterIndex = i;
                formatMessage = (String) args[i];
                continue;
            }

            if (formatMessageParameterIndex == -1) {
                // 首先得到的就是message
                continue;
            }

            if (argumentParameterIndex == -1) {
                argumentParameterIndex = i;
                continue;
            }

            if (parameterType == Throwable.class) {
                exceptionParameterIndex = i;
                ex = (Throwable) args[i];
            }
        }

        final Object[] parameters = this.filterParameters(args, formatMessageParameterIndex, exceptionParameterIndex);
        logger.log(fqcn, slf4jLogger, level, formatMessage, ex, parameters);
        return method.invoke(this.slf4jLogger, args);
    }


    /**
     * 从{@code args}中过滤得到调用{@link Logger}相关函数的可变长参数
     *
     * @param args          参数
     * @param fromIndex     参数预计开始位置
     * @param exceptToIndex 参数预计结束位置（异常开始的位置）
     * @return {@code Object[] }
     */
    private Object[] filterParameters(Object[] args, int fromIndex, int exceptToIndex) {
        if (null == args) {
            return null;
        }

        final int length = args.length;
        if (fromIndex > length - 1) {
            return null;
        }

        final int toIndex = (exceptToIndex == -1 ? length : exceptToIndex);
        final List<Object> parameters = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            if (i >= length - 1) {
                return parameters.toArray();
            }
            final Object arg = args[i];
            parameters.add(arg);
        }

        return parameters.toArray();
    }
}
