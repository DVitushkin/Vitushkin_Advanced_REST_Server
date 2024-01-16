package com.dunice.Vitushkin_Advanced_REST_Server.models;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "log_event")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "request_uri")
    private String requestUri;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "http_protocol")
    private String httpProtocol;

    @Column(name = "country")
    private String country;

    @Column(name = "java_method")
    private String javaMethod;

    @Column(name = "status_code")
    private String statusCode;

    @Nullable
    @Column(name = "exception")
    private String exception;
}
