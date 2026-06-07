package vn.leoo.common.dto;

import lombok.Setter;

import java.util.Objects;

@Setter
public abstract class PageBase {
    private Integer page;
    private Integer size;

    public Integer getPage() {
        return Objects.requireNonNullElse(page,1);
    }

    public Integer getSize() {
        return Objects.requireNonNullElse(size,25);
    }
}
