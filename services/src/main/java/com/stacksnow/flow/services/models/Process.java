package com.stacksnow.flow.services.models;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "processes")
public class Process extends EntityWithUUID {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Flow flow;

    private long livyJobId;

    private Status status;
}
