package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.sber.services.FirstService
import ru.sber.services.SecondService
import ru.sber.services.ThirdService

@Configuration
@ComponentScan
open class ServicesConfig {
    @Bean
    open fun firstService(): FirstService {
        return FirstService()
    }

    @Bean
    open fun secondService(): SecondService {
        return SecondService()
    }
    @Bean
    open fun thirdService(): ThirdService {
        return ThirdService()
    }
}

@Configuration
@ComponentScan("ru.sber.anotherservices")
open class AnotherServicesConfig