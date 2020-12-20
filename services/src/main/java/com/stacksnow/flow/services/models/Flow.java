package com.stacksnow.flow.services.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity(name = "flows")
public class Flow extends EntityWithUUID {
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private DAG dag;

    private String name;

    private Date updatedDate;

    public Flow() {
        this.updatedDate = new Date();
    }

    public Flow(DAG dag, String name) {
        this();
        this.dag = dag;
        this.name = name;
    }

    public Flow(DAG dag) {
        this();
        this.dag = dag;
    }
}
