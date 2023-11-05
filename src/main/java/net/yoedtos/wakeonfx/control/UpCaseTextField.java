package net.yoedtos.wakeonfx.control;

public final class UpCaseTextField extends LimitedTextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate())
            super.replaceText(start, end, text.toUpperCase());
    }
}
