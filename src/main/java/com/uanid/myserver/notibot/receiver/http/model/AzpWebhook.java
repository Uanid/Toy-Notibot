
package com.uanid.myserver.notibot.receiver.http.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "subscriptionId",
    "notificationId",
    "id",
    "eventType",
    "publisherId",
    "message",
    "detailedMessage",
    "resource",
    "resourceVersion",
    "resourceContainers",
    "createdDate"
})
@Getter
@Setter
@ToString
public class AzpWebhook {

    @JsonProperty("subscriptionId")
    private String subscriptionId;
    @JsonProperty("notificationId")
    private Long notificationId;
    @JsonProperty("id")
    private String id;
    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("publisherId")
    private String publisherId;
    @JsonProperty("message")
    private Message message;
    @JsonProperty("detailedMessage")
    private DetailedMessage detailedMessage;
    @JsonProperty("resource")
    private Resource resource;
    @JsonProperty("resourceVersion")
    private String resourceVersion;
    @JsonProperty("resourceContainers")
    private ResourceContainers resourceContainers;
    @JsonProperty("createdDate")
    private String createdDate;
}
