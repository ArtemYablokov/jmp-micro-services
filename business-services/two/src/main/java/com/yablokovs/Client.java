package com.yablokovs;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("one-application")
public interface Client {
    @RequestMapping("/business/one")
    String getOne();
}