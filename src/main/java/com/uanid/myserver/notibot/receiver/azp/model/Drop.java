
package com.uanid.myserver.notibot.receiver.azp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "location",
    "type",
    "url",
    "downloadUrl"
})
@Getter
@Setter
@ToString
public class Drop {

    @JsonProperty("location")
    private String location;
    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;
    @JsonProperty("downloadUrl")
    private String downloadUrl;

}
