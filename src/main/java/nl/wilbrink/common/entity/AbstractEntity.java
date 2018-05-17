package nl.wilbrink.common.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    @Column
    private Long version;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @CreatedDate
    @Column
    private LocalDateTime created;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    @LastModifiedDate
    @Column
    private LocalDateTime updated;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
}
