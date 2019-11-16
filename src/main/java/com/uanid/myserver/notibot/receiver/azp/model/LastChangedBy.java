
package com.uanid.myserver.notibot.receiver.azp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "displayName",
    "url",
    "id",
    "uniqueName",
    "imageUrl"
})
@Getter
@Setter
@ToString
public class LastChangedBy {

    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("url")
    private String url;
    @JsonProperty("id")
    private String id;
    @JsonProperty("uniqueName")
    private String uniqueName;
    @JsonProperty("imageUrl")
    private String imageUrl;

}
