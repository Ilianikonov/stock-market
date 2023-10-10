package com.example.stockmarket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title ="stock-market",
        description = "My API", version = "1.0.0",
        contact = @Contact(
            name = "Ilia",
            email = "nikonov.ilia2013@yandex.ru"
        )
    )
)
public class SwaggerConfig {

}
