package it.polito.wa2.ticketing.observability.config

import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.aop.ObservedAspect
import io.micrometer.core.instrument.MeterRegistry
import it.polito.wa2.ticketing.observability.aop.AbstractObserveAroundMethodHandler
import it.polito.wa2.ticketing.observability.aop.DefaultObserveAroundMethodHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.aspectj.lang.ProceedingJoinPoint

/* enable spring scan for the annotation @Observed , 
 * which will make the class/method also handled by the related class of Prometheus 
 * to send trace data or prepare metrics data
 */
@Configuration(proxyBeanMethods = false)
class ObserveConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(AbstractObserveAroundMethodHandler::class)
    fun observeAroundMethodHandler(): AbstractObserveAroundMethodHandler {
        return DefaultObserveAroundMethodHandler()
    }

    @Bean
    @ConditionalOnMissingBean(ObservedAspect::class)
    fun observedAspect(observationRegistry: ObservationRegistry?): ObservedAspect {
        return ObservedAspect(observationRegistry!!)
    }
}