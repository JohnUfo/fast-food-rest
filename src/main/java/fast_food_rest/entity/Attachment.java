package fast_food_rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import fast_food_rest.entity.template.AbsEntity;

import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attachment extends AbsEntity {

    @Column(nullable = false)
    private String fileOriginalName;

    @Column(nullable = false)
    @Min(value = 1, message = "Size must be greater than 0")
    private long size;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private AttachmentContent attachmentContent;

}
