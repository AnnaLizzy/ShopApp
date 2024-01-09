package com.example.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass// annotation trong Java Persistence API (JPA)
// được sử dụng để đánh dấu một lớp trừu tượng (abstract class)
// là một lớp cơ sở ánh xạ cho các entity.
public class BaseEntity {
    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;
    @PrePersist
    protected void onCreate(){
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updateAt =LocalDateTime.now();
    }
}
