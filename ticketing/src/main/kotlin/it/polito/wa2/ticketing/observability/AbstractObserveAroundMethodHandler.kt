package it.polito.wa2.ticketing.observability

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationHandler
import io.micrometer.observation.aop.ObservedAspect
import org.aspectj.lang.ProceedingJoinPoint

class AbstractObserveAroundMethodHandler : AbstractLogAspect(),
        ObservationHandler<ObservedAspect.ObservedAspectContext> {

    override fun onStart(context: ObservedAspect.ObservedAspectContext) {
        /* we can get many information (including class, arguments...)
        from ProceedingJoinPoint. */
        val joinPoint: ProceedingJoinPoint = context.proceedingJoinPoint
        super.logBefore(joinPoint)
    }

    override fun onStop(context: ObservedAspect.ObservedAspectContext) {
        val joinPoint: ProceedingJoinPoint = context.proceedingJoinPoint
        super.logAfter(joinPoint)
    }

    override fun supportsContext(context: Observation.Context): Boolean {
        /* required, otherwise this will handle the
        non-spring bean method (e.g. handling http.server.requests)
        and throw a class cast exception. */
        return context is ObservedAspect.ObservedAspectContext
    }
}