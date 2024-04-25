package org.netprophet.springserver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Document(indexName = "network_flow")
public class NetworkFlow implements Serializable {

    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String srcIp;
    @Field(type = FieldType.Integer)
    private Integer srcPort;
    @Field(type = FieldType.Text)
    private String destIp;
    @Field(type = FieldType.Integer)
    private Integer destPort;
    @Field(type = FieldType.Integer)
    private Integer protocol;
    @Field(type = FieldType.Integer)
    private Integer packetsIn;
    @Field(type = FieldType.Integer)
    private Integer bytesIn;
    @Field(type = FieldType.Integer)
    private Integer packetsOut;
    @Field(type = FieldType.Integer)
    private Integer bytesOut;
    @Field(type = FieldType.Double)
    private Double duration;
    @Field(type = FieldType.Date_Nanos)
    private Instant collectedDateTime;

    public NetworkFlow() {
        collectedDateTime = Instant.now();
    }
}
