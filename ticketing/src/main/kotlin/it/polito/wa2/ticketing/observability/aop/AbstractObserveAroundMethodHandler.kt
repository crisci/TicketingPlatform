package it.polito.wa2.ticketing.observability.aop

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationHandler
import io.micrometer.observation.aop.ObservedAspect.ObservedAspectContext
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Autowired

/*
 * log methods and arguments
*/ 
open class AbstractObserveAroundMethodHandler : AbstractLogAspect(), ObservationHandler<ObservedAspectContext> {

    override fun onStart(context: ObservedAspectContext) {
        val joinPoint = context.proceedingJoinPoint
        super.logBefore(joinPoint)
    }

    override fun onStop(context: ObservedAspectContext) {
        val joinPoint = context.proceedingJoinPoint
        super.logAfter(joinPoint)
    }

    override fun supportsContext(context: Observation.Context): Boolean {
        return context is ObservedAspectContext
    }
}