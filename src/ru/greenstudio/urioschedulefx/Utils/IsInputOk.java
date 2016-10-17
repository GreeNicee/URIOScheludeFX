package ru.greenstudio.urioschedulefx.Utils;

import javafx.scene.control.TextField;

public class IsInputOk {
    public enum TextFieldType {STANDARD, NUMERIC}

    public static boolean isTextFieldOk(TextField textField) {
        return textField.getLength() != 0;
    }

    public static boolean isTextFieldOk(TextField textField, TextFieldType type) {
        switch (type) {
            case STANDARD:
                return textField.getLength() != 0;
            case NUMERIC:
                boolean boo = true;
                CharSequence charArr = textField.getCharacters();
                for (int i = 0; i < charArr.length(); i++) {
                    if (!Character.isDigit(charArr.charAt(i))) {
                        boo = false;
                        break;
                    }
                }
                return boo;
            default:
                return textField.getLength() != 0;
        }
    }
}
