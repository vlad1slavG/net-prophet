package org.netprophet.springserver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Getter
@Setter
@Document(indexName = "host")
public class Host {

    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String hostName;
    @Field(type = FieldType.Text)
    private String hostIp;
    @Field(type = FieldType.Date_Nanos)
    private Instant lastActiveDateTime;

    public Host(String hostName, String hostIp) {
        this.hostName = hostName;
        this.hostIp = hostIp;
        this.lastActiveDateTime = Instant.now();
    }
}
