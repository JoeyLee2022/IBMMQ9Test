package com.example.ibmmq.ibmmq_test.controller;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class RequestObject {
    private String id;
    private String message;
    private String mqName;
    private String managerName;
}
