package com.taskify.dtos;

import com.taskify.models.FunctionModel;
import com.taskify.models.MetaFieldModel;
import com.taskify.models.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FunctionMetadataDto {

    private Integer id;

    private Integer functionMetadataTemplateId;

    private String metadataTitle;

    private String metadataDescription;

    private Integer metadataAssignedToUserId;

    private boolean isFunctionMetadataDone;

    private Date functionMetadataFinishedDate;

    private Integer functionMetadataFinishedByUserId;

    private Integer functionModelId;

    private List<MetaFieldModel> metaFieldModelList;

}
