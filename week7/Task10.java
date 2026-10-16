package week7;
interface ValidationStrategy {
    boolean isValid(String password);
}

class LengthValidator implements ValidationStrategy {
    public boolean isValid(String password) {
        return password.length() >= 8;
    }
}

class DigitValidator implements ValidationStrategy {
    public boolean isValid(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= '0' && c <= '9') {
                return true;
            }
        }
        return false;
    }
}

class NoSpaceValidator implements ValidationStrategy {
    public boolean isValid(String password) {
        return !password.contains(" ");
    }
}
