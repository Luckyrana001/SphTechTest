package com.sphtech.application.model;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class MobileDataUsageErrorResponse {






    @JsonProperty("help")
    private String help;
    @JsonProperty("success")
    private String success;

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @JsonProperty("error")
    public  Error error;


    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    class Error
    {

        @JsonProperty("__type")
        private String __type;

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }


        @JsonProperty("__extras")
        private List<String> extra = new ArrayList<>();

        public List<String> getExtra() {
            return extra;
        }

        public void setExtra(List<String> extra) {
            this.extra = extra;
        }



    }





}
 /*

    {
    "help": "https://data.gov.sg/api/3/action/help_show?name=datastore_search",
    "success": false,
    "error": {
        "__type": "Validation Error",
        "__extras": [
            "invalid value \"limitf\""
        ]
    }
}

*/