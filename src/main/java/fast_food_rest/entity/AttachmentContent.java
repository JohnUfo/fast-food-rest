package fast_food_rest.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import fast_food_rest.entity.template.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentContent extends AbsEntity {


    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;

    @OneToOne(mappedBy = "attachmentContent")
    private Attachment attachment;
}
