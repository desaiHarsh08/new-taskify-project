package com.taskify.dtos;

import com.taskify.models.FunctionMetadataModel;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MetaFieldDto {

    private Integer id;

    private String fieldName;

    private String fieldValue;

}
