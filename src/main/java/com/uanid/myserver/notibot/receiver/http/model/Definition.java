
package com.uanid.myserver.notibot.receiver.http.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "batchSize",
    "triggerType",
    "definitionType",
    "id",
    "name",
    "url"
})
@Getter
@Setter
@ToString
public class Definition {

    @JsonProperty("batchSize")
    private Long batchSize;
    @JsonProperty("triggerType")
    private String triggerType;
    @JsonProperty("definitionType")
    private String definitionType;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
}
