package io.github.greenhandsw.common.entity.option;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 返回结果里的编码和详细信息的模板
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Result {
    SUCCESS(0, "成功"),
    ;

    private Integer code;
    private String message;

}
