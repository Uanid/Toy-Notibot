
package com.uanid.myserver.notibot.receiver.azp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "queueType",
    "id",
    "name",
    "url"
})
@Getter
@Setter
@ToString
public class Queue {

    @JsonProperty("queueType")
    private String queueType;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;

}
