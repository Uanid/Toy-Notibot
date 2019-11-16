
package com.uanid.myserver.notibot.receiver.azp.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uri",
    "id",
    "buildNumber",
    "url",
    "startTime",
    "finishTime",
    "reason",
    "status",
    "dropLocation",
    "drop",
    "log",
    "sourceGetVersion",
    "lastChangedBy",
    "retainIndefinitely",
    "hasDiagnostics",
    "definition",
    "queue",
    "requests"
})
@Getter
@Setter
@ToString
public class Resource {

    @JsonProperty("uri")
    private String uri;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("buildNumber")
    private String buildNumber;
    @JsonProperty("url")
    private String url;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("finishTime")
    private String finishTime;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("status")
    private String status;
    @JsonProperty("dropLocation")
    private String dropLocation;
    @JsonProperty("drop")
    private Drop drop;
    @JsonProperty("log")
    private Log log;
    @JsonProperty("sourceGetVersion")
    private String sourceGetVersion;
    @JsonProperty("lastChangedBy")
    private LastChangedBy lastChangedBy;
    @JsonProperty("retainIndefinitely")
    private Boolean retainIndefinitely;
    @JsonProperty("hasDiagnostics")
    private Boolean hasDiagnostics;
    @JsonProperty("definition")
    private Definition definition;
    @JsonProperty("queue")
    private Queue queue;
    @JsonProperty("requests")
    private List<Request> requests = new ArrayList<Request>();
}
