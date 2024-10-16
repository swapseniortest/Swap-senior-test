package com.swap.issues.recovery.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private static final List<String> SENSITIVE_DATA = Arrays.asList("Authorization", "x-access-token", "password");

    private final ObjectMapper objectMapper;

    @Pointcut("within(com.swap.issues.recovery.api..*) || within(com.swap.issues.recovery.client..*)")
    public void applicationPackagePointcut() {
    }

    @Pointcut("within(@org.springframework.stereotype.Component *) || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springComponentPointcut() {
    }

    @Around("applicationPackagePointcut() && springComponentPointcut()")
    public Object logAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        var packeageClass = formatDeclaringType(joinPoint);
        var method = joinPoint.getSignature().getName();
        var requestArguments = filterArgs(joinPoint);

        log.info("Iniciando {}.{}() - Argumentos: {}", packeageClass, method, requestArguments);

        var stopWatch = new StopWatch();
        stopWatch.start();
        try {
            var result = joinPoint.proceed();
            var responseArguments = toJsonResult(maskSensitiveFields(result));
            log.info("{}.{}() - Resultado: {}", packeageClass, method, responseArguments);
            return result;
        } finally {
            stopWatch.stop();
            log.info("Finalizando {}.{}() - Tempo de processamento: {} ms",
                    packeageClass,
                    method,
                    stopWatch.getTotalTimeMillis()
            );
        }
    }

    public List<String> filterArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<String> filteredArgs = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof String && dontExistsSensitiveFields(arg.toString())) {
                filteredArgs.add(arg.toString());
            } else if (arg != null) {
                var object = maskSensitiveFields(arg);
                filteredArgs.add(toJsonResult(object));
            }
        }
        return filteredArgs;
    }

    private Object maskSensitiveFields(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (!field.getDeclaringClass().getPackageName().startsWith("java.")) {
                    field.setAccessible(true);

                    if (SENSITIVE_DATA.contains(field.getName())) {
                        field.set(obj, "****");
                    }
                }
            }
        } catch (IllegalAccessException | InaccessibleObjectException e) {
            log.error("Erro ao mascarar campos sensíveis: ", e);
        }

        return obj;
    }

    public Boolean dontExistsSensitiveFields(String field) {
        return !SENSITIVE_DATA.contains(field);
    }

    private String formatDeclaringType(final JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName().replace("com.swap.issues.recovery", "");
    }

    private String toJsonResult(final Object object) {
        if (object == null) {
            return null;
        }

        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (final Exception ex) {
            log.warn("Erro ao converter objeto para json - {}", ex.getMessage());
        }

        return toStringResult(object);
    }

    private String toStringResult(final Object o) {
        if (o == null) {
            return null;
        }
        try {
            return this.removeToStringNullValues(o.toString());
        } catch (final Exception ex) {
            log.warn("Erro ao extrair valores nulos da string para registrar log - {}", ex.getMessage());
        }
        return o.toString();
    }

    public String removeToStringNullValues(final String resultToString) {
        if (isBlank(resultToString)) {
            return resultToString;
        }

        return resultToString
                .replaceAll("(?<=(,\\s|\\())[^\\s(]+?=null(?:, )?", StringUtils.EMPTY)
                .replaceAll(",\\s\\)", ")");
    }
}