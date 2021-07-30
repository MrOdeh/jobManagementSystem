package com.payoneer.dev.jobmanagementsystem.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {

    public static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", length = 36, updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    @Column(name = "creation_date", nullable = false ,updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "last_update_date",nullable = false)
    private Timestamp updatedAt;
}
