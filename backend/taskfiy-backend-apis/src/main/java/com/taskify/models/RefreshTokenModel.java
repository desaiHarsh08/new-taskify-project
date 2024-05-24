package com.taskify.models;

import com.taskify.app.AppConstants;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = AppConstants.REFRESH_TOKEN_TABLE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class RefreshTokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String refreshToken;

    private Instant expiry;

    @OneToOne
    private UserModel userModel;

}
