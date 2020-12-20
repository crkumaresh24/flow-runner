package com.stacksnow.flow.services.models;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class EntityWithUUID {
    @Id
    @Type(type = "pg-uuid")
    private UUID id;

    public EntityWithUUID() {
        this.id = UUID.randomUUID();
    }
}
