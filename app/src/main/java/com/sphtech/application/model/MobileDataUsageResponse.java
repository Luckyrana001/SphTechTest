package com.sphtech.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class MobileDataUsageResponse {
    public MobileDataUsageResponse() {

    }

    @JsonProperty("help")
    String help;


    @JsonProperty("success")
    boolean success;


    @JsonProperty("result")
    Result result = new Result();

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


}




/* {
    "help": "https://data.gov.sg/api/3/action/help_show?name=datastore_search",
    "success": true,
    "result": {
        "resource_id": "a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
        "fields": [
            {
                "type": "int4",
                "id": "_id"
            },
            {
                "type": "text",
                "id": "quarter"
            },
            {
                "type": "numeric",
                "id": "volume_of_mobile_data"
            }
        ],
        "records": [
            {
                "volume_of_mobile_data": "0.012635",
                "quarter": "2007-Q1",
                "_id": 11
            },
            {
                "volume_of_mobile_data": "0.029992",
                "quarter": "2007-Q2",
                "_id": 12
            },
            {
                "volume_of_mobile_data": "0.053584",
                "quarter": "2007-Q3",
                "_id": 13
            },
            {
                "volume_of_mobile_data": "0.100934",
                "quarter": "2007-Q4",
                "_id": 14
            },
            {
                "volume_of_mobile_data": "0.171586",
                "quarter": "2008-Q1",
                "_id": 15
            }
        ],
        "_links": {
            "start": "/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=5",
            "prev": "/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=5&offset=5",
            "next": "/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=5&offset=15"
        },
        "offset": 10,
        "limit": 5,
        "total": 56
    }
}*/