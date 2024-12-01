package com.alumnimanagement.config;

import com.alumnimanagement.entity.Job;
import com.alumnimanagement.entity.User;
import com.alumnimanagement.web.dto.JobDTO;
import com.alumnimanagement.web.dto.UserDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaConfig {

    @Bean
    public MapperFactory mapperFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(User.class, UserDTO.class)
                .field("address.city","city")
                .field("address.state","state")
                .field("address.country","country")
                .field("company.name","companyName")
                .byDefault()
                .register();

        return mapperFactory;
    }

    @Bean
    public MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }

}


