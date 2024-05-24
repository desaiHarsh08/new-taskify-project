package com.taskify.models;

import com.taskify.app.AppConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = AppConstants.ACTIVITY_LOGS_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivityLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private Date date;

    private String type;

    private String action;

    private String message;

}
