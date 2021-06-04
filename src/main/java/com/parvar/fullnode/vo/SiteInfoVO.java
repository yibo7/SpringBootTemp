package com.parvar.fullnode.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.parvar.fullnode.vo.serializerjson.SiteName;
import lombok.Data;

@Data
public class SiteInfoVO {
    @JsonSerialize(using = SiteName.class)
    @JsonProperty("name")
    private String siteName;

    @JsonProperty("id")
    private Integer siteId;

    @JsonProperty("type")
    private Integer siteType;
}
