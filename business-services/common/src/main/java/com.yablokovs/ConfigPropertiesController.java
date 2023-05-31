package com.yablokovs;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigPropertiesController {

    private DynamicStringProperty propertyOneWithDynamic
            = DynamicPropertyFactory.getInstance()
            .getStringProperty("baeldung.archaius.properties.one", "not found!");

    @GetMapping("/property")
    public String getPropertyValue() {
        return propertyOneWithDynamic.getName() + ": " + propertyOneWithDynamic.get();
    }
}