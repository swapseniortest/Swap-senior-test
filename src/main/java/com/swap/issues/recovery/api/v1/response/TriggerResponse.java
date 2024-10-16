package com.swap.issues.recovery.api.v1.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TriggerResponse {

    private String trigger;
    private Date nextExecution;
}
