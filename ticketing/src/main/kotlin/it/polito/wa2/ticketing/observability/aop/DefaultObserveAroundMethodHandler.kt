package it.polito.wa2.ticketing.observability.aop

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.aspectj.lang.ProceedingJoinPoint


class DefaultObserveAroundMethodHandler : AbstractObserveAroundMethodHandler()