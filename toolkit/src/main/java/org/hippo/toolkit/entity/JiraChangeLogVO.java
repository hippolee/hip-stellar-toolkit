package org.hippo.toolkit.entity;

public class JiraChangeLogVO {

    private String field;
    private String from;
    private String to;

    public JiraChangeLogVO(String field, String from, String to) {
        this.field = field;
        this.from = from;
        this.to = to;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
