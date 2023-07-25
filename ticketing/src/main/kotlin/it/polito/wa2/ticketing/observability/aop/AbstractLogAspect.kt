package it.polito.wa2.ticketing.observability.aop

import jakarta.annotation.Nullable
import jakarta.validation.constraints.NotNull
import org.aspectj.lang.ProceedingJoinPoint
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import io.micrometer.core.instrument.MeterRegistry


/*
 * The extended class AbstractLogAspect is for the classes that I didn’t put the 
 * @Observed on it, but still want to log around them. 
 * So I extract the logic for others @Aspect to use.
*/

open class AbstractLogAspect {
    
    @Throws(Throwable::class)
    open fun logInfoAround(joinPoint: ProceedingJoinPoint): Any? {
        val logInfo = getLogInfo(joinPoint)
        val log = LoggerFactory.getLogger(logInfo.declaringType)
        logBefore(logInfo, log)
        val `object` = joinPoint.proceed()
        logAfter(logInfo, log)
        return `object`
    }

    fun logBefore(joinPoint: ProceedingJoinPoint) {
        val logInfo = getLogInfo(joinPoint)
        val log = LoggerFactory.getLogger(logInfo.declaringType)
        logBefore(logInfo, log)
    }

    fun logAfter(joinPoint: ProceedingJoinPoint) {
        val logInfo = getLogInfo(joinPoint)
        val log = LoggerFactory.getLogger(logInfo.declaringType)
        logAfter(logInfo, log)
    }

    @JvmRecord
    private data class LogInfo(val declaringType: @NotNull Class<*>?, val className: @NotNull String?, val annotatedMethodName: @NotNull String?, @field:Nullable @param:Nullable val args: Array<Any>)
    companion object {
        private fun getLogInfo(joinPoint: ProceedingJoinPoint): LogInfo {
            val signature = joinPoint.signature
            val declaringType = signature.declaringType
            val className = declaringType.simpleName
            val annotatedMethodName = signature.name
            val args = joinPoint.args
            return LogInfo(declaringType, className, annotatedMethodName, args)
        }

        private fun logBefore(logInfo: LogInfo, log: Logger) {
            log.info("[{}.{}] start ({})", logInfo.className, logInfo.annotatedMethodName, logInfo.args)
        }

        private fun logAfter(logInfo: LogInfo, log: Logger) {
            log.info("[{}.{}] end", logInfo.className, logInfo.annotatedMethodName)
        }
    }
}