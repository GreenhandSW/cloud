package io.github.greenhandsw.core.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VideoCommentRelationKey implements Serializable {
    private Long videoId;
    private Long id;
}

