package io.github.greenhandsw.common.entity.option;


import lombok.Getter;

@Getter
public enum Vote {
    // 没有点赞或点踩
    NONE(0),
    // 点赞
    UPVOTE(1),
    // 点踩
    DOWNVOTE(2),
    ;
    private int value;

    Vote(int value) {
        this.value=value;
    }
}
