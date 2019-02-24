package com.sphtech.application.common;


public class DialogModel {
    private boolean showed;
    private String title;
    private String content;
    private String positiveBtnText;
    private String negBtnText;
    private OnPositiveBtnClicked onPositiveBtnClicked;
    private OnNegativeBtnClicked onNegativeBtnClicked;

    public DialogModel(boolean showed, String title, String content, String positiveBtnText, String negBtnText, OnPositiveBtnClicked onPositiveBtnClicked, OnNegativeBtnClicked onNegativeBtnClicked) {
        this.showed = showed;
        this.title = title;
        this.content = content;
        this.positiveBtnText = positiveBtnText;
        this.negBtnText = negBtnText;
        this.onPositiveBtnClicked = onPositiveBtnClicked;
        this.onNegativeBtnClicked = onNegativeBtnClicked;
    }


    public static DialogModel hideModel() {
        return new DialogModel(false, "", "", "", "", null, null);
    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPositiveBtnText() {
        return positiveBtnText;
    }

    public void setPositiveBtnText(String positiveBtnText) {
        this.positiveBtnText = positiveBtnText;
    }

    public String getNegBtnText() {
        return negBtnText;
    }

    public void setNegBtnText(String negBtnText) {
        this.negBtnText = negBtnText;
    }

    public OnPositiveBtnClicked getOnPositiveBtnClicked() {
        return onPositiveBtnClicked;
    }

    public void setOnPositiveBtnClicked(OnPositiveBtnClicked onPositiveBtnClicked) {
        this.onPositiveBtnClicked = onPositiveBtnClicked;
    }

    public OnNegativeBtnClicked getOnNegativeBtnClicked() {
        return onNegativeBtnClicked;
    }

    public void setOnNegativeBtnClicked(OnNegativeBtnClicked onNegativeBtnClicked) {
        this.onNegativeBtnClicked = onNegativeBtnClicked;
    }

    @Override
    public String toString() {
        return "DialogModel{" +
                "showed=" + showed +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", positiveBtnText='" + positiveBtnText + '\'' +
                ", negBtnText='" + negBtnText + '\'' +
                ", onPositiveBtnClicked=" + onPositiveBtnClicked +
                ", onNegativeBtnClicked=" + onNegativeBtnClicked +
                '}';
    }

    @FunctionalInterface
    public interface OnPositiveBtnClicked {
        void onclicked();
    }

    @FunctionalInterface
    public interface OnNegativeBtnClicked {
        void onClicked();
    }
}
