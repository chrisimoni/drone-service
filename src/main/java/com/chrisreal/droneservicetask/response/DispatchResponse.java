package com.chrisreal.droneservicetask.response;

import java.util.List;

import com.chrisreal.droneservicetask.model.Dispatch;
import com.chrisreal.droneservicetask.model.Drone;
import com.chrisreal.droneservicetask.model.Medication;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DispatchResponse extends BaseResponse{
    Medication medication;
	Drone drone;
    Dispatch dispatch;
    List<Dispatch> dispatches;
    Integer count;


    public DispatchResponse(String code, String message, Dispatch dispatch) {
        super(code, message);
        this.dispatch = dispatch;
    }

    public DispatchResponse(Dispatch dispatch) {
        this.dispatch = dispatch;
    }

    public DispatchResponse(String code, String message) {
        super(code, message);
    }

    public DispatchResponse(String code, String message, List<Dispatch> dispatches) {
        super(code, message);
        this.dispatches = dispatches;
        this.count = dispatches.size();
    }

}
