package it.polito.wa2.ticketing.observability

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.Signature
import org.slf4j.LoggerFactory

open class AbstractLogAspect {

    fun logBefore(joinPoint: ProceedingJoinPoint) {
        val logInfo = getLogInfo(joinPoint)
        val log = LoggerFactory.getLogger(logInfo.declaringType)
        log.info("[{}.{}] start ({})", logInfo.className, logInfo.annotatedMethodName, logInfo.args)
    }

    private fun getLogInfo(joinPoint: ProceedingJoinPoint): LogInfo {
        val signature: Signature = joinPoint.signature
        val declaringType: Class<*> = signature.declaringType
        val className: String = declaringType.simpleName
        val annotatedMethodName: String = signature.name
        val args: Array<Any>? = joinPoint.args
        return LogInfo(declaringType, className, annotatedMethodName, args)
    }

    fun logAfter(joinPoint: ProceedingJoinPoint) {
        val logInfo = getLogInfo(joinPoint)
        val log = LoggerFactory.getLogger(logInfo.declaringType)
        log.info("[{}.{}] end", logInfo.className, logInfo.annotatedMethodName)
    }

    private data class LogInfo(
            val declaringType: Class<*>,
            val className: String,
            val annotatedMethodName: String,
            val args: Array<Any>?
    )
}