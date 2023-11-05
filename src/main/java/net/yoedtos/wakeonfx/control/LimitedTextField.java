package net.yoedtos.wakeonfx.control;

import javafx.scene.control.TextField;

public class LimitedTextField extends TextField {

    protected int limit;

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate())
            super.replaceText(start, end, text);
    }

    @Override
    public void replaceSelection(String replacement) {
        if (validate())
            super.replaceSelection(replacement);
    }

    protected boolean validate() {
        return lengthProperty().lessThan(limit).get();
    }

    public int getLimit() {
        return limit;
    }

    public int limitProperty() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
