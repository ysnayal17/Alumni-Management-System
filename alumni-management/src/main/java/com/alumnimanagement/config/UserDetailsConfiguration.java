package com.alumnimanagement.config;

import com.alumnimanagement.enums.Department;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Data
public class UserDetailsConfiguration {

    public static Map<String,String> getBasicHeaders(){
        Map<String,String> mapping = new HashMap<>();
        mapping.put("Name","name");
        mapping.put("Date Of Graduation", "dateOfGraduation");
        mapping.put("Department", "department");
        mapping.put("MobileNo", "mobileNo");
        mapping.put("Email", "email");
        return mapping;
    }

    public static Map<String,Class<?>> getDatatype(){
        Map<String, Class<?>> entityFieldDatatypeMap = new HashMap<>();
        entityFieldDatatypeMap.put("name", String.class);
        entityFieldDatatypeMap.put("dateOfGraduation", LocalDate.class);
        entityFieldDatatypeMap.put("department", Department.class);
        entityFieldDatatypeMap.put("mobileNo", String.class);
        entityFieldDatatypeMap.put("email", String.class);
        return entityFieldDatatypeMap;
    }
}
