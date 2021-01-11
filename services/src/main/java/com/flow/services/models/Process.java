package com.flow.services.models;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;

@Data
@Entity(name = "processes")
public class Process extends EntityWithUUID {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Flow flow;

    private long livyJobId;

    private Status status;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, String> processProperties;
}
