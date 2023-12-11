package io.github.greenhandsw.common.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserVideoRelationKey implements Serializable {
    private Long userId;
    private Long videoId;
}
