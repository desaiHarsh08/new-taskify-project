package com.taskify.models;

import com.taskify.app.AppConstants;
import com.taskify.models.templates.FunctionTemplateModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = AppConstants.FUNCTION_METADATA_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FunctionMetadataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String metadataTitle;

    private String metadataDescription;

    @ManyToOne(targetEntity = UserModel.class)
    private UserModel metadataAssignedToUser;

    private boolean isFunctionMetadataDone;

    private Date functionMetadataFinishedDate;

    @ManyToOne(targetEntity = UserModel.class)
    private UserModel functionMetadataFinishedByUser;

    @ManyToOne(targetEntity = FunctionModel.class)
    private FunctionModel functionModel;

}
